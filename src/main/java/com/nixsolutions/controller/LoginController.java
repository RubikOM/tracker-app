package com.nixsolutions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.nixsolutions.pojo.Pages;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return Pages.LOGIN_PAGE.getPage();
    }
}
