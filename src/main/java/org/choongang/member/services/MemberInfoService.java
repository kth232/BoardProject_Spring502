package org.choongang.member.services;

import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberInfo;
import org.choongang.member.constants.Authority;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {
    //회원 정보 알려주는 서비스, UserDetailsService 구현체
    //시큐리티도 프레임워크이기 때문에 틀이 정해져 있다

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //회원 정보가 없을 때 userName이 없는 예외 처리
        Member member = memberRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(username));

        //인가=접근 통제
        //특정 인물만 특정 자원을 접근하도록 해야 함->권한 정보 필요
        List<Authorities> tmp = Objects.requireNonNullElse(member.getAuthorities(),
                List.of(Authorities.builder().member(member).authority(Authority.USER).build()));
        //기본 권한은 일반 회원


        List<SimpleGrantedAuthority> authorities = tmp.stream()
                .map(a->new SimpleGrantedAuthority(a.getAuthority().name()))
                .toList();

        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }
}
