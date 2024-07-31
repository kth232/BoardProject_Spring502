package org.choongang.member.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestLogin { //로그인 커맨드 객체
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private boolean success = true;

    private String code; //코드를 가지고 글로벌 에러 처리, 메세지가 없는 경우 코드 그대로 출력
    private String defaultMessage;
    
    private String redirectUrl; //로그인 성공 시 이동할 주소
}
