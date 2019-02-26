package com.nixsolutions.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nixsolutions.entity.EnglishWord;
import com.nixsolutions.pojo.Pages;
import com.nixsolutions.service.EnglishWordService;

@Controller
public class EnglishWordsController {
    private static final String OUTPUT_FILE_NAME = "output.txt";

    private final EnglishWordService englishWordService;

    @Autowired
    public EnglishWordsController(EnglishWordService englishWordService) {
        this.englishWordService = englishWordService;
    }

    @GetMapping("/")
    public String getPage(Model model) {
        model.addAttribute("englishWord", new EnglishWord());
        model.addAttribute("words", englishWordService.getAllWords());
        return Pages.ENGLISH_WORD_PAGE.getPage();
    }

    @PostMapping("/createWord")
    public String addWordToDb(EnglishWord englishWord) {
        englishWordService.addWord(englishWord);
        return "redirect:/";
    }

    // TODO change this method after
    // TODO try Baeldong's with text Plain
    @GetMapping(value = "/getTxtFile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getTxtFile(HttpServletResponse response) {
        response.setHeader("Content-Disposition","attachment; filename=" + OUTPUT_FILE_NAME);
        StringBuilder content = new StringBuilder();

        List<EnglishWord> words = englishWordService.getAllWords();
        for(EnglishWord word: words) {
            content.append(word.getWordAsString());
        }
        return new String(content.toString().getBytes(), StandardCharsets.UTF_8);
    }
}
