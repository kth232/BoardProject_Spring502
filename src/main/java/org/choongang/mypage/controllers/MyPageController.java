package org.choongang.mypage.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.services.MemberSaveService;
import org.choongang.mypage.validators.ProfileUpdateValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final ProfileUpdateValidator profileUpdateValidator;
    private final MemberSaveService memberSaveService;
    private final MemberUtil memberUtil;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("addCss", List.of("mypage/mypageStyle"));
        return "front/mypage/index";
    }

    @GetMapping("/info")
    public String info(@ModelAttribute RequestProfile form) {

        Member member = memberUtil.getMember();
        form.setUserName(member.getUserName());
        form.setMobile(member.getMobile());

        return "front/mypage/info";
    }


    @PostMapping("/info")
    public String updateInfo(@Valid RequestProfile form,
                             Errors errors) {

        profileUpdateValidator.validate(form, errors);

        if (errors.hasErrors()) {
            return "front/mypage/info";
        }

        memberSaveService.save(form);

        //SecurityContextHolder.getContext().setAuthentication();

        return "redirect:/mypage";
    }
}