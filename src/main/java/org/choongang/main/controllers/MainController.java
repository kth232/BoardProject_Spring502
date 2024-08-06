package org.choongang.main.controllers;

import org.choongang.global.exceptions.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/") //메인 경로
public class MainController implements ExceptionProcessor {

    @GetMapping
    public String index(Model model) {
        //스프링 연계 데이터-다른 범위로 바꿀 수 있는 기능이 탑재되어 있기 때문에 사용

        model.addAttribute("addCommonScript", List.of("fileManager"));
        //List.of() ->자동으로 front에 있는 걸로 완성됨
        model.addAttribute("addScript", List.of("test/form"));
        return "front/main/index"; //주소가 아닌 템플릿 경로
    }

}
