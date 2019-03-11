package com.nixsolutions.controller;


import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.pojo.Pages;
import com.nixsolutions.service.DictionaryService;
import com.nixsolutions.service.UserService;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {
    private final DictionaryService dictionaryService;
    private final UserService userService;

    public DictionaryController(@Autowired DictionaryService dictionaryService, @Autowired UserService userService) {
        this.dictionaryService = dictionaryService;
        this.userService = userService;
    }

    @GetMapping
    public String getPage(Model model, Principal principal) {
        model.addAttribute("dictionaryElement", new DictionaryElement());
        List<DictionaryElement> elements = dictionaryService.getTodaysDictionaryElements();
        if (!elements.isEmpty()) {
            model.addAttribute("todaysAddedElements", elements);
        } else {
            model.addAttribute("lastAddedElements", dictionaryService.getLastDictionaryElementsWords());
        }
        model.addAttribute("username", principal.getName());
        return Pages.DICTIONARY_PAGE.getPage();
    }

    @PostMapping("/createWord")
    public String addDictionaryElement(@Valid DictionaryElement dictionaryElement, BindingResult bindingResult,
                                       Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("todaysAddedElements", dictionaryService.getTodaysDictionaryElements());
            return Pages.DICTIONARY_PAGE.getPage();
        } else {
            dictionaryElement.setAuthor(userService.findByLogin(principal.getName()));
            dictionaryService.addDictionaryElement(dictionaryElement);
            return "redirect:/dictionary";
        }
    }

    @DeleteMapping("/delete/{deleteWord}")
    @ResponseBody
    public void removeDictionaryElement(@PathVariable("deleteWord") String word) {
        dictionaryService.removeDictionaryElement(word);
    }

    @GetMapping("/edit/{editWord}")
    public String getEditDictionaryElementPage(@PathVariable("editWord") String word, Model model) {
        model.addAttribute("dictionaryElement", dictionaryService.findByWord(word));
        return Pages.EDIT_WORD_PAGE.getPage();
    }
}
