package com.rubinskyi.controller.translator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.rubinskyi.pojo.Pages;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return Pages.LOGIN_PAGE.getPage();
    }

    @GetMapping("/successful")
    public String showDictionaryPAge() {
        return "redirect:/dictionary";
    }
}
