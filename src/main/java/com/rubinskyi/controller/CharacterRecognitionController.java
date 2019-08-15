package com.rubinskyi.controller;

import com.rubinskyi.config.properties.OcrProperties;
import com.rubinskyi.pojo.Pages;
import com.rubinskyi.service.ImageCharacterRecognitionService;
import com.rubinskyi.service.api.FileTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.rubinskyi.pojo.constant.StringConstant.ERROR;

@Slf4j
@Controller
@RequestMapping("/dictionary")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CharacterRecognitionController {
    private final ImageCharacterRecognitionService recognitionService;
    private final FileTranslationService fileTranslationService;
    private final OcrProperties ocrProperties;

    @GetMapping("/file")
    public String getFileImportationPage() {
        return Pages.UPLOAD_FILE_PAGE.getPage();
    }

    @PostMapping("/uploadFile")
    public String submitFile(@RequestParam("file") MultipartFile multipartFile, Model model, Principal principal) {
        String pathToFile = ocrProperties.getUserUploadedImagesFolder().getAbsolutePath() + "_" + principal.getName() + "_" + multipartFile.getOriginalFilename();
        File file = new File(pathToFile);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error("Error while transferring org.springframework.web.multipart.MultipartFile to java.io.File ", e);
            model.addAttribute(ERROR, ocrProperties.getWrongFileFormatMessage());
            return Pages.UPLOAD_FILE_PAGE.getPage();
        }
        String textFromRecognitionService = recognitionService.resolveImage(file);
        if (textFromRecognitionService.equals(ocrProperties.getEmptyResponseMessage())) {
            model.addAttribute(ERROR, ocrProperties.getCannotRecogniseCharactersMessage());
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
