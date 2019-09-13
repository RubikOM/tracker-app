package com.rubinskyi.controller;

import com.rubinskyi.pojo.Pages;
import com.rubinskyi.service.outerApi.FileTranslationService;
import com.rubinskyi.service.outerApi.ImageCharacterRecognitionService;
import com.rubinskyi.util.file.FileSearcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Map;

import static com.rubinskyi.pojo.constant.StringConstant.EMPTY_RESPONSE;
import static com.rubinskyi.pojo.constant.StringConstant.ERROR;

@Slf4j
@Controller
@RequestMapping("/dictionary")
@RequiredArgsConstructor
public class CharacterRecognitionController {
    private final ImageCharacterRecognitionService recognitionService;
    private final FileTranslationService fileTranslationService;
    private final FileSearcher fileSearcher;
    private static final String IMAGE_FOLDER_NAME = "tessimage";

    @GetMapping("/file")
    public String getFileImportationPage() {
        return Pages.UPLOAD_FILE_PAGE.getPage();
    }

    @PostMapping("/uploadFile")
    public String submitFile(@RequestParam("file") MultipartFile multipartFile, Model model, Principal principal) {
        String tessimageFolderPath = fileSearcher.getFilePathByName(IMAGE_FOLDER_NAME);
        log.info("Trying to get file! ");
        log.info("Absolute path: " + tessimageFolderPath);
        String fileName = tessimageFolderPath + "\\" + principal.getName() + "_" + multipartFile.getOriginalFilename();
        log.info("File to create: " + fileName);
        File file = new File(fileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error("Error while transferring org.springframework.web.multipart.MultipartFile to java.io.File ", e);
            model.addAttribute(ERROR, "Cannot read given file, check if it has required format");
            return Pages.UPLOAD_FILE_PAGE.getPage();
        }
        String textFromRecognitionService = recognitionService.resolveImage(file);
        if (textFromRecognitionService.equals(EMPTY_RESPONSE)) {
            model.addAttribute(ERROR, "Sorry, we can't recognise characters in this image, try else image");
        } else {
            Map<String, Object> translations = fileTranslationService.getTranslations(textFromRecognitionService, principal.getName());
            model.addAttribute("recognisedText", textFromRecognitionService);
            model.addAttribute("russianTranslation", translations.get("russian"));
            model.addAttribute("suggestedElements", translations.get("suggestedElements"));
        }

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            log.error("Cannot delete file " + file.getPath(), e);
        }
        return Pages.UPLOAD_FILE_PAGE.getPage();
    }
}
