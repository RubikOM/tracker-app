package com.rubinskyi.controller.translator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping({"", "/createWord"})
    public String redirectToDictionaryController(){
        return "redirect:/dictionary";
    }
}
