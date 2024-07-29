package org.choongang.Member.controllers;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestJoin { //커맨드 객체 구성, 커맨드 객체는 사용자가 입력한 값을 전달->검증 주로 작업함
    //커맨드 객체의 검증 기본 메세지는 간단하게 만들 때 그냥 사용해도 되지만 안예쁘기 때문에 작업 추가하면 좋음
    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String userName;

    @NotBlank
    private String mobile;

    @AssertTrue
    private boolean agree;
}
