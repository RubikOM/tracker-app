package com.rubinskyi.controller.translator;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;
import com.rubinskyi.service.DictionaryService;
import com.rubinskyi.service.UserService;

@Controller
public class ImportationFileController {
    private static final String TODAY_FILE_NAME = LocalDate.now().toString() + ".txt";
    private static final String ALL_TIME_WORDS_FILE_NAME = "all your words.txt";

    private final DictionaryService dictionaryService;
    private final UserService userService;

    public ImportationFileController(@Autowired DictionaryService dictionaryService, @Autowired UserService userService) {
        this.dictionaryService = dictionaryService;
        this.userService = userService;
    }

    @GetMapping(value = "/getTxtFile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getTodayTxtFile(HttpServletResponse response, Principal principal) {
        User authenticatedUser = userService.findByLogin(principal.getName());

        response.setHeader("Content-Disposition", "attachment; filename=" + TODAY_FILE_NAME);
        List<DictionaryElement> dictionaryElements = dictionaryService.getTodaysDictionaryElements(authenticatedUser);

        return makeSingleStringWithFiles(dictionaryElements);
    }

    @GetMapping(value = "/getAllTimeTxtFile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getAllTimeTxtFile(HttpServletResponse response, Principal principal) {
        User authenticatedUser = userService.findByLogin(principal.getName());

        response.setHeader("Content-Disposition", "attachment; filename=" + ALL_TIME_WORDS_FILE_NAME);
        List<DictionaryElement> allTimeDictionaryElements = dictionaryService.getAllDictionaryElements(authenticatedUser);

        return makeSingleStringWithFiles(allTimeDictionaryElements);
    }

    private String makeSingleStringWithFiles(List<DictionaryElement> dictionaryElements) {
        StringBuilder content = new StringBuilder();
        dictionaryElements.forEach(dictionaryElement -> content.append(dictionaryElement.getDictionaryElementAsString()));

        return content.toString();
    }
}
