package com.rubinskyi.controller;

import com.rubinskyi.entity.User;
import com.rubinskyi.service.FileImportationService;
import com.rubinskyi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileImportationController {
    private static final String FILE_EXTENSION = ".txt";
    private static final String FILE_TYPE = "text/plain;charset=UTF-8";

    private final UserService userService;
    private final FileImportationService fileImportationService;

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
