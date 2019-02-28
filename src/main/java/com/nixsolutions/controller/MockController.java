package com.nixsolutions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MockController {

    @GetMapping("")
    public String redirectToDictionaryController(){
        return "redirect:/dictionary";
    }
}
