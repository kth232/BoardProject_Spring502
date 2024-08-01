package org.choongang.global.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.choongang.global.exceptions.script.AlertBackException;
import org.choongang.global.exceptions.script.AlertException;
import org.choongang.global.exceptions.script.AlertRedirectException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public interface ExceptionProcessor {

    @ExceptionHandler(Exception.class)
    default ModelAndView errorHandler(Exception e, HttpServletRequest request) {
        System.out.println("여기1");
        ModelAndView mv = new ModelAndView();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; //기본 응답 코드 500
        String tpl = "error/error"; //기본 에러 페이지

        System.out.println(e);

        if(e instanceof CommonException commonException) {
            status = commonException.getStatus();
            System.out.println("여기2");
            if (e instanceof AlertException) { //AlertException의 하위 클래스 예외인 경우
                tpl = "common/_execute_script"; //JS형태의 메세지인 경우
                String script = String.format("alert('%s');", e.getMessage()); //형식화해서 메세지로 대체
                System.out.println("여기3");
                if (e instanceof AlertBackException alertBackException) {
                    script += String.format("%s.history.back();", alertBackException.getTarget());
                    //;붙여서 문장 끝내기
                }
                if (e instanceof AlertRedirectException alertRedirectException) {
                    //script += String.format("%S.location.replace('%s');", alertRedirectException.getTarget(), alertRedirectException.getUrl());
                    //외부 자원은 contextPath가 필요하지만 내부 자원은 없어도 됨
                    String url = alertRedirectException.getUrl();

                    if(url.startsWith("http")) { // 외부 URL이 아닌 경우
                        url = request.getContextPath() + url;
                    }

                    script += String.format("%s.location.replace('%s');", alertRedirectException.getTarget(), url);
                }
                
                mv.addObject("script", script); //스크립트 형태로 출력
            }
        } else if (e instanceof AccessDeniedException) { //AccessDeniedException 예외로 처리되도록 함
            status = HttpStatus.UNAUTHORIZED;
        }

        String url = request.getRequestURI();
        String qs = request.getQueryString();

        if (StringUtils.hasText(qs)) url += "?" + qs;


        mv.addObject("message", e.getMessage());
        mv.addObject("status", status.value());
        mv.addObject("method", request.getMethod());
        mv.addObject("path", url);
        mv.setStatus(status);
        mv.setViewName(tpl);

        return mv;
    }
}