package com.rubinskyi.controller.translator;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.service.api.FormFillingService;

@RestController
public class FormFillingController {
    private final FormFillingService formFillingService;

    @Autowired
    public FormFillingController(FormFillingService formFillingService) {
        this.formFillingService = formFillingService;
    }

    @GetMapping("/fillPage/{wordInEnglish}")
    public DictionaryElement returnWordTranslationFromApi(@PathVariable String wordInEnglish, Principal principal) {
        DictionaryElement dictionaryElementFromApi = formFillingService.getDictionaryElementFromApi(wordInEnglish, principal);
        // TODO spellchecker here : if word isn't correct guess what user wanted to add + Some normal way to show error
        // TODO NPE protection here? Optional etc
        if (dictionaryElementFromApi.getTranslation().isEmpty()) {
            // TODO returning this looks very-very bad
            return new DictionaryElement.Builder(wordInEnglish, "!!! We can't find this word in DB, please try something else").build();
        }
        else return dictionaryElementFromApi;
    }
}
