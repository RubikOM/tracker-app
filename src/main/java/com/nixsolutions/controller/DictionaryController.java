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
import com.nixsolutions.entity.User;
import com.nixsolutions.pojo.Pages;
import com.nixsolutions.service.DictionaryService;
import com.nixsolutions.service.UserService;

@Controller
@RequestMapping("/dictionary")
public class DictionaryController {
    private final DictionaryService dictionaryService;
    private final UserService userService;

    private User authenticatedUser = null;

    public DictionaryController(@Autowired DictionaryService dictionaryService, @Autowired UserService userService) {
        this.dictionaryService = dictionaryService;
        this.userService = userService;
    }

    @GetMapping
    public String getPage(Model model, Principal principal) {
        if (!model.containsAttribute("dictionaryElement")) {
            model.addAttribute("dictionaryElement", new DictionaryElement());
        }

        User user = userService.findByLogin(principal.getName());
        if (authenticatedUser == null) authenticatedUser = user;

        List<DictionaryElement> elements = dictionaryService.getTodaysDictionaryElements(user);
        if (!elements.isEmpty()) {
            model.addAttribute("todaysAddedElements", elements);
        } else {
            model.addAttribute("lastAddedElements", dictionaryService.getLastDictionaryElements(user));
        }

        model.addAttribute("username", principal.getName());
        return Pages.DICTIONARY_PAGE.getPage();
    }

    @GetMapping("/edit/{editWord}")
    public String getEditDictionaryElementPage(@PathVariable("editWord") String word, Model model, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        if (authenticatedUser == null) authenticatedUser = user;

        model.addAttribute("dictionaryElement", dictionaryService.findByWord(word, authenticatedUser));

        return Pages.EDIT_WORD_PAGE.getPage();
    }

    @PostMapping("/createWord")
    public String createDictionaryElement(@Valid DictionaryElement dictionaryElement, BindingResult bindingResult,
                                          Model model, Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("todaysAddedElements", dictionaryService.getTodaysDictionaryElements(authenticatedUser));
            return Pages.DICTIONARY_PAGE.getPage();
        } else {
            User author = userService.findByLogin(principal.getName());
            dictionaryService.createDictionaryElement(dictionaryElement, author);

            return "redirect:/dictionary";
        }
    }

    @DeleteMapping("/delete/{deleteWord}")
    @ResponseBody
    public void removeDictionaryElement(@PathVariable("deleteWord") String word) {
        dictionaryService.removeDictionaryElement(word, authenticatedUser);
    }
}
