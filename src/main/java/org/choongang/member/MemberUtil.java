package org.choongang.member;

import org.choongang.member.constants.Authority;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberUtil { //편의기능 정의, 매번 일일이 적기 힘들기 때문에 간편하게 사용하기 위함
    public boolean isLogin() {
        return getMember() != null;
    }

    public boolean isAdmin() {
        if(isLogin()) {
            Member member = getMember();
            List<Authorities> authorities = member.getAuthorities();
            return authorities.stream().anyMatch(s -> s.getAuthority() == Authority.ADMIN);
        }
        return false;
    }

    public Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //authentication 객체가 null이 아니고 권한이 부여되어 있으면서 memberInfo에 있는 경우
        if(authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof MemberInfo) {
            MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
            return memberInfo.getMember();
        }

        return null;
    }
}
