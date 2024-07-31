package org.choongang.member;

import lombok.Builder;
import lombok.Data;
import org.choongang.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {
    //UserDetails 구현체

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities; //인가
    private Member member;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    //필요에 따라 맞게 추가 설정
    @Override
    public boolean isAccountNonExpired() {
        return true; //계정 만료 유무 체크
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //계정 일정 시간 경과 시 차단
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //비번 유효기간 만료 시 초기화->보안상 비번 바꿀 때
    }

    @Override
    public boolean isEnabled() {
        return true;//회원 탈퇴 여부 체크, 탈퇴 시 false
    }
    //해당 값에 따라 예외 처리 다양함->후속 처리 다름
}
