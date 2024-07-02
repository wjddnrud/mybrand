package com.example.demo.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "")
public class LoginController {



    // 로그인 폼
    @GetMapping(value = "/mybrand")
    public void loginForm() { }

    //로그인 요청
    @PostMapping(value = "/login")
    @ResponseBody
    public void login() {}

}
