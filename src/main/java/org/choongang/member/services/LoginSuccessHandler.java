package org.choongang.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    //인터페이스 이름 기억하기!

    //로그인 성공 시 유입되는 메서드
    //로그인한 사용자의 인증 정보를 담고 있는 객체: Authentication
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession(); //세션 값 가져오기
        //세션에 남아있는 requestLogin 값 제거
        session.removeAttribute("requestLogin"); //로그인 하다가 중간에 나가는 경우는 세션값 그대로 남아있을 수 있다

        //로그인 성공 시 redirectUrl이 있으면 해당 주소로 이동, 아니면 메인 페이지로 이동함
        String redirectUrl = request.getParameter("redirectUrl");
        redirectUrl = StringUtils.hasText(redirectUrl) ? redirectUrl.trim() : "/"; //trim()으로 공백 문자 제거

        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}
