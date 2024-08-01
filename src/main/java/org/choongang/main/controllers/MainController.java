package org.choongang.main.controllers;

import org.choongang.global.exceptions.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") //메인 경로
public class MainController implements ExceptionProcessor {

    @GetMapping
    public String index() {

        return "front/main/index"; //주소가 아닌 템플릿 경로
    }

}
