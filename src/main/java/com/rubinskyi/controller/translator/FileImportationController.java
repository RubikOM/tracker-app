package com.rubinskyi.controller.translator;

import java.security.Principal;
import java.time.LocalDate;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rubinskyi.entity.User;
import com.rubinskyi.service.FileImportationService;
import com.rubinskyi.service.UserService;

@Controller
public class FileImportationController {
    private static final String FILE_EXTENSION = ".txt";
    private static final String FILE_TYPE = "text/plain;charset=UTF-8";

    private final UserService userService;
    private final FileImportationService fileImportationService;

    @Autowired
    public FileImportationController(UserService userService, FileImportationService fileImportationService) {
        this.userService = userService;
        this.fileImportationService = fileImportationService;
    }

    @GetMapping(value = "/getTxtFile", produces = FILE_TYPE)
    @ResponseBody
    public String getTodayTxtFile(HttpServletResponse response, Principal principal) {
        User authenticatedUser = userService.findByLogin(principal.getName());
        String fileName = authenticatedUser.getLogin() + LocalDate.now().toString() + FILE_EXTENSION;
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        return fileImportationService.getTodayTxtFile(authenticatedUser);
    }

    @GetMapping(value = "/getAllTimeTxtFile", produces = FILE_TYPE)
    @ResponseBody
    public String getAllTimeTxtFile(HttpServletResponse response, Principal principal) {
        User authenticatedUser = userService.findByLogin(principal.getName());
        String fileName = authenticatedUser.getLogin() + "'s all words" + FILE_EXTENSION;
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        return fileImportationService.getAllTimeTxtFile(authenticatedUser);
    }
}
