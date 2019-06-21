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
public class FileImportationController {

    private final DictionaryService dictionaryService;
    private final UserService userService;

    public FileImportationController(@Autowired DictionaryService dictionaryService, @Autowired UserService userService) {
        this.dictionaryService = dictionaryService;
        this.userService = userService;
    }

    @GetMapping(value = "/getTxtFile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getTodayTxtFile(HttpServletResponse response, Principal principal) {
        User authenticatedUser = userService.findByLogin(principal.getName());
        String fileName = authenticatedUser.getLogin() + LocalDate.now().toString() + ".txt";

        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        List<DictionaryElement> dictionaryElements = dictionaryService.getTodaysDictionaryElements(authenticatedUser);

        return makeSingleStringWithFiles(dictionaryElements);
    }

    @GetMapping(value = "/getAllTimeTxtFile", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getAllTimeTxtFile(HttpServletResponse response, Principal principal) {
        User authenticatedUser = userService.findByLogin(principal.getName());
        String fileName = authenticatedUser.getLogin() + "'s all words.txt";

        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        List<DictionaryElement> allTimeDictionaryElements = dictionaryService.getAllDictionaryElements(authenticatedUser);

        return makeSingleStringWithFiles(allTimeDictionaryElements);
    }

    // TODO check later if /n is needed and rename method
    private String makeSingleStringWithFiles(List<DictionaryElement> dictionaryElements) {
        StringBuilder content = new StringBuilder();
        dictionaryElements.forEach(dictionaryElement -> content.append(dictionaryElement.importer().getDictionaryElementAsString()).append("/n"));

        return content.toString();
    }
}
