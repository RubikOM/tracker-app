package com.rubinskyi.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
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

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import com.rubinskyi.pojo.Pages;
import com.rubinskyi.service.DictionaryElementService;
import com.rubinskyi.service.UserService;

@Controller
@RequestMapping("/dictionary")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DictionaryController {
    private final DictionaryElementService dictionaryElementService;
    private final UserService userService;

    @GetMapping
    public String getPage(Model model, Principal principal) {
        model.addAttribute("dictionaryElement", new DictionaryElement());
        User user = userService.findByLogin(principal.getName());

        List<DictionaryElement> elements = dictionaryElementService.getTodaysDictionaryElements(user);
        if (!elements.isEmpty()) {
            Collections.reverse(elements);
            model.addAttribute("todaysAddedElements", elements);
        } else {
            model.addAttribute("lastAddedElements", dictionaryElementService.getLastDictionaryElements(user));
        }

        model.addAttribute("username", user.getLogin());
        return Pages.DICTIONARY_PAGE.getPage();
    }

    @GetMapping("/edit/{editWord}")
    public String getEditPage(@PathVariable("editWord") String word, Model model, Principal principal) {
        User user = userService.findByLogin(principal.getName());

        model.addAttribute("dictionaryElement", dictionaryElementService.findByWord(word, user));

        return Pages.EDIT_WORD_PAGE.getPage();
    }

    @PostMapping("/createWord")
    public String createDictionaryElement(@Valid DictionaryElement dictionaryElement, BindingResult bindingResult,
                                          Model model, Principal principal) {
        User authenticatedUser = userService.findByLogin(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("todaysAddedElements", dictionaryElementService.getTodaysDictionaryElements(authenticatedUser));
            return Pages.DICTIONARY_PAGE.getPage();
        } else {
            dictionaryElementService.createDictionaryElement(dictionaryElement, authenticatedUser);
            return "redirect:/dictionary";
        }
    }

    @DeleteMapping("/delete/{deleteWord}")
    @ResponseBody
    public void removeDictionaryElement(@PathVariable("deleteWord") String word, Principal principal) {
        User authenticatedUser = userService.findByLogin(principal.getName());

        dictionaryElementService.removeDictionaryElement(word, authenticatedUser);
    }
}
