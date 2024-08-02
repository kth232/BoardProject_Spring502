package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.choongang.file.services.repositories.BoardRepository;
import org.choongang.global.exceptions.ExceptionProcessor;
import org.choongang.global.exceptions.script.AlertRedirectException;
import org.choongang.member.MemberUtil;
import org.choongang.member.services.MemberSaveService;
import org.choongang.member.validators.JoinValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@SessionAttributes("requestLogin")
public class MemberController implements ExceptionProcessor { //ExceptionProcessor로 우선 유입됨
    //필요한 컨트롤러에 ExceptionProcessor 구현해서 예외 처리
    //컨트롤러는 컨트롤러의 역할만!, DB처리는 다른 곳에서 하는 것이 좋다=단일책임원칙

    private final JoinValidator joinValidator; //의존성 주입
    private final MemberSaveService memberSaveService;
    private final MemberUtil memberUtil;
    private final BoardRepository boardRepository; //테스트용이기 때문에 실제로 만들 땐 추가하지 말 것!

    @ModelAttribute
    public RequestLogin requestLogin() {
        return new RequestLogin();
    }

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form) {

        //예외 테스트
        boolean result = false;
        if(!result) {
            //throw new AlertException("test exception", HttpStatus.BAD_REQUEST);
            throw new AlertRedirectException("test exception", "/mypage", HttpStatus.BAD_REQUEST);
        }

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
    public String login(@Valid @ModelAttribute RequestLogin form, Errors errors) {
        //세션 범위로 속성 저장->모델로 값 유지
        String code = form.getCode();
        if (StringUtils.hasText(code)) {
            errors.reject(code, form.getDefaultMessage());

            //비번 만료인 경우 비번 재설정 페이지 이동
            if (code.equals("CredentialsExpired.Login")) {
                return "redirect:/member/password/reset";
            }
        }

        return "front/member/login";
    }

    @GetMapping("/test6")
    @ResponseBody
    @PreAuthorize("isAuthenticated()") //특정 메서드에 권한 통제 가능, 메서드 실행 전 인증 수행
    public void test06() {
        log.info("test6-회원만 접근 가능");

    }

    @GetMapping("/test7")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('ADMIN')") //특정 메서드에 권한 통제 가능, 메서드 실행 전 인증 수행
    public void test07() {
        log.info("test7-관리자만 접근 가능");

    }


/*
    @ResponseBody //일반 컨트롤러에서 void를 사용하고 싶을 때
    @GetMapping("/test")
    public void test(Principal principal) {
        //로그인 한 상태에서 test 페이지로 이동할 경우 로그인한 회원 정보 확인
        log.info("login ID: {}", principal.getName());
    }

    @ResponseBody //일반 컨트롤러에서 void를 사용하고 싶을 때
    @GetMapping("/test2")
    public void test2(@AuthenticationPrincipal MemberInfo memberInfo) {
        //요청 메서드에 주입하는 방법, 더 자주 사용
        log.info("login member: {}", memberInfo.toString());
    }

    @ResponseBody
    @GetMapping("/test3")
    public void test3() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("로그인 상태: {}", authentication.isAuthenticated());
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof MemberInfo) {
            // 로그인 상태 - UserDetails 구현체(getPrincipal())

            MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
            log.info("로그인 회원: {}", memberInfo.toString());
        } else { // 미로그인 상태 - String / anonymousUser (getPrincipal())
            //anonymous: 미로그인 사용자
            log.info("getPrincipal(): {}", authentication.getPrincipal());
        }
    }

    @ResponseBody
    @GetMapping("/test4")
    public void test4() {
        log.info("로그인 여부: {}", memberUtil.isLogin());
        log.info("로그인 회원: {}", memberUtil.getMember());
    }

    //테스트용
    @ResponseBody
    @GetMapping("/test5")
    public void test5() {
//        Board board = Board.builder()
//                .bId("freetalk")
//                .bName("자유게시판")
//                .build();
//
//        boardRepository.saveAndFlush(board);

        Board board = boardRepository.findById("freetalk").orElse(null);
        board.setBName("(mod)자유게시판");
        board.setModifiedBy(memberUtil.getMember().getUserName());

        boardRepository.saveAndFlush(board);
    }
*/
}