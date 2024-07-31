package org.choongang.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.choongang.member.controllers.RequestLogin;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    //추상 메서드 있음->필수 구현, 인터페이스 이름 기억하기!

    //로그인 실패 시 유입되는 메서드
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //세션을 사용하려면 쿠키에 있는 데이터 가져와야 함
        HttpSession session = request.getSession();

        RequestLogin form = new RequestLogin();
        form.setEmail(request.getParameter("email"));
        form.setPassword(request.getParameter("password"));

        //회원 상태에 따라 예외처리
        if (exception instanceof BadCredentialsException) {
            //아이디 또는 비밀번호가 일치하지 않는 경우
            form.setCode("BadCredentials.Login");
        } else if (exception instanceof DisabledException) {
            //탈퇴한 회원->회원을 비활성화
            form.setCode("Disabled.Login");
        } else if (exception instanceof CredentialsExpiredException) {
            //비밀번호 유효기간 만료
            form.setCode("CredentialsExpired.Login");
        } else if (exception instanceof AccountExpiredException) {
            //사용자 계정 유효기간 만료
            form.setCode("AccountExpried.Login");
        } else if (exception instanceof LockedException) {
            //사용자 계정이 잠겨있는 경우
            form.setCode("Locked.Login");
        } else {
            form.setCode("Fail.Login");
        }

        form.setDefaultMessage(exception.getMessage()); //디폴트 메세지 처리

        //System.out.println(exception);

        form.setSuccess(false);
        session.setAttribute("requestLogin", form);

        //로그인 실패 시 로그인 페이지 이동
        response.sendRedirect(request.getContextPath() + "/member/login");
    }
}
