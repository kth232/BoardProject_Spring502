package org.choongang.Member;

import lombok.Builder;
import lombok.Data;
import org.choongang.Member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {
    //중요한 추상 메서드 3가지

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
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
        return true; //비번 만료 시 재설정
    }

    @Override
    public boolean isEnabled() {
        return true;//회원 탈퇴 여부 체크
    }
}
