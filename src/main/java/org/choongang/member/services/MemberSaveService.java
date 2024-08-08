package org.choongang.member.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.constants.Authority;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.choongang.member.exceptions.MemberNotFoundException;
import org.choongang.member.repositories.AuthoritiesRepository;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.mypage.controllers.RequestProfile;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional //연달아 쿼리문 수행 시 사용
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberUtil memberUtil;
    private final HttpSession session;

    /**
     * 회원가입 처리
     * @param form
     */
    public void save(RequestJoin form){
        Member member = new ModelMapper().map(form, Member.class);
        String hash = passwordEncoder.encode(member.getPassword());//Bcrypt 해시화
        member.setPassword(hash);

        save(member, List.of(Authority.USER));
    }

    /**
     * 회원정보 수정
     * @param form
     */
    public void save(RequestProfile form) {
        Member member = memberUtil.getMember();
        String email = member.getEmail();
        member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        String password = form.getPassword();
        String mobile = form.getMobile();
        if (StringUtils.hasText(mobile)) {
            mobile = mobile.replaceAll("\\D", "");
        }

        member.setUserName(form.getUserName());
        member.setMobile(mobile);

        if (StringUtils.hasText(password)) {
            String hash = passwordEncoder.encode(password);
            member.setPassword(hash);
        }

        memberRepository.saveAndFlush(member);

        session.setAttribute("userInfoChanged", true);
    }

    //특정 아이템을 찾는 것보다 비우고 새로 추가하는 방식->간단함
    public void save(Member member, List<Authority> authorities) {

        //전화번호 숫자만 기록, 형식을 통일성 있게 해야 함->조회할 때 좋음
        String mobile = member.getMobile();
        if (StringUtils.hasText(mobile)) {
            mobile = mobile.replaceAll("\\D", "");
            member.setMobile(mobile);
        }

        memberRepository.saveAndFlush(member);

        //권한 추가, 수정 S
        if(authorities != null){ //권한이 있는 경우만 정보를 한번 가져와서 삭제함
            List<Authorities> items = authoritiesRepository.findByMember(member);
            authoritiesRepository.deleteAll(items);
            authoritiesRepository.flush();

            items = authorities.stream().map(a-> Authorities.builder()
                    .member(member)
                    .authority(a)
                    .build()).toList();

            authoritiesRepository.saveAllAndFlush(items);
            //권한 추가, 수정 E
        }
    }
}
