package com.nixsolutions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping({"", "/dictionary/createWord"})
    public String redirectToDictionaryController(){
        return "redirect:/dictionary";
    }
}
