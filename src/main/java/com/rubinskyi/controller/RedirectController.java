package com.rubinskyi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping({"", "/createWord"})
    public String redirectToDictionaryPage(){
        return "redirect:/dictionary";
    }

    @GetMapping({"/dictionary/uploadFile"})
    public String redirectToCharacterRecognitionPage(){
        return "redirect:/dictionary/file";
    }
}
