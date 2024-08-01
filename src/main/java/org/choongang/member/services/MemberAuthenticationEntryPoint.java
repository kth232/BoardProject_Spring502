package org.choongang.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println(authException);

        /**
         * 회원 전용 페이지로 접근한 경우 = /mypage -> 로그인 페이지로 이동
         * 관리자 페이지로 접근한 경우 - 응답코드 401, 에러페이지 출력
         */
        String uri = request.getRequestURI(); //요청 주소를 보면 접근한 페이지가 어디인지 알 수 있다
        if(uri.contains("/admin")) { //uri에 /admin이 포함된 경우 ->관리자 페이지
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else { //회원 전용 페이지
            String qs = request.getQueryString();
            String redirectUrl = uri.replace(request.getContextPath(), ""); //uri에 들어있는 주소에 contextPath 지우기
            if(StringUtils.hasText(qs)) {
                redirectUrl += "?" + qs;
            }
            response.sendRedirect(request.getContextPath() + "/member/login?redirect=" + redirectUrl);
            //현재 접근한 주소가 uri에 있음->contextPath를 지우고 쿼리스트링을 추가해서 원래 이용하려고 했던 페이지로 이동시킴
        }
    }
}
