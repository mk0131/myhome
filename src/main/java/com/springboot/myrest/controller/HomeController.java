package com.springboot.myrest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping // 작성 안하면 기본으로 설정
    public String index() {
        return "index";
    }
}
