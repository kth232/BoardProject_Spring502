package org.choongang.global.advices;

import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice("org.choongang") //범위 설정
@RequiredArgsConstructor
public class CommonControllerAdvice { //전역에서 확인 가능
    private final MemberUtil memberUtil;

    @ModelAttribute("loggedMember")
    public Member loggedMember() {
        return memberUtil.getMember();
    }

    @ModelAttribute("isLogin")
    public boolean isLogin() {
        return memberUtil.isLogin();
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return memberUtil.isAdmin();
    }

    @ModelAttribute("myProfileImage")
    public FileInfo myProfileImage() {
        if (isLogin()) {
            Member member = memberUtil.getMember();
            return member.getProfileImage();
        }

        return null;
    }
}
