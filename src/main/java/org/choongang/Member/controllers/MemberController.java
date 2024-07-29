package org.choongang.Member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.Member.services.MemberSaveService;
import org.choongang.Member.validators.JoinValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController{
    //컨트롤러는 컨트롤러의 역할만!, DB처리는 다른 곳에서 하는 것이 좋다=단일책임원칙

    private final JoinValidator joinValidator; //의존성 주입
    private final MemberSaveService memberSaveService;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form) {
        return "front/member/join";
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors) { //커맨드 객체는 입력받은 값을 전달할 때 생성됨
        //앞글자가 el식 형태로 소문자로 바뀜, 검증 작업 수행, 오류 발생 시 Errors에 넘김

        joinValidator.validate(form, errors); //추가 검증 수행

        if(errors.hasErrors()) { //에러 발생 시 다시 회원가입 페이지로 이동
            return "front/member/join";
        }

        memberSaveService.save(form); //회원 가입 처리

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login() {

        return "front/member/login";
    }
}
