package com.rubinskyi.controller.translator;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.pojo.dto.DictionaryElementDto;
import com.rubinskyi.service.api.TranslationFromApiService;

@RestController
public class TranslationFromApiController {
    private final TranslationFromApiService translationFromApiService;

    @Autowired
    public TranslationFromApiController(TranslationFromApiService translationFromApiService) {
        this.translationFromApiService = translationFromApiService;
    }

    @GetMapping("/fillPage/{wordInEnglish}")
    public DictionaryElementDto getDictionaryElementFromApi(@PathVariable String wordInEnglish, Principal principal) {
        DictionaryElement dictionaryElementFromApi = translationFromApiService.getDictionaryElementFromApi(wordInEnglish, principal);

        if (dictionaryElementFromApi.getTranslation().isEmpty()) {
            // TODO this message from props
            return new DictionaryElementDto(wordInEnglish, "Sorry, we didn't find anything, check your spelling");
        } else return new DictionaryElementDto(dictionaryElementFromApi);
    }
}
