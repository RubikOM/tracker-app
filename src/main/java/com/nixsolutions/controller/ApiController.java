package com.nixsolutions.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.service.UserService;
import com.nixsolutions.service.api.ApiService;

@RestController
public class ApiController {
    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService, UserService userService) {
        this.apiService = apiService;
    }

    @GetMapping("/fillPage/{wordInEnglish}")
    public DictionaryElement returnWordTranslationFromApi(@PathVariable String wordInEnglish, Principal principal) {
        return apiService.getDictionaryElementFromApi(wordInEnglish, principal);
    }
}
