package org.choongang.global.configs;

import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final MemberUtil memberUtil;

    @Override
    public Optional<String> getCurrentAuditor() {
        String email = memberUtil.isLogin() ? memberUtil.getMember().getEmail() : null;
        //로그인 상태라면 회원 정보 중 이메일 정보 받아오기, 아니면 null

        return Optional.ofNullable(email);
        //ofNullable: null값도 있을 수 있음
    }
}
