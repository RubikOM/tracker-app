package com.rubinskyi.controller;

import com.rubinskyi.pojo.Pages;
import com.rubinskyi.service.ImageCharacterRecognitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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

@Controller
@RequestMapping("/dictionary")
@PropertySource("classpath:characterRecognition.properties")
public class CharacterRecognitionController {
    private final ImageCharacterRecognitionService recognitionService;
    @Value("classpath:tessimage")
    private File userUploadedImagesFolder;
    @Value("${emptyResponse}")
    private String emptyResponseMessage;
    @Value("${wrongFileType}")
    private String wrongFileFormatMessage;
    @Value("${cannotRecogniseCharacters}")
    private String cannotRecogniseCharactersMessage;
    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterRecognitionController.class);

    @Autowired
    public CharacterRecognitionController(ImageCharacterRecognitionService recognitionService) {
        this.recognitionService = recognitionService;
    }

    @GetMapping("/file")
    public String getFileImportationPage() {
        return Pages.UPLOAD_FILE_PAGE.getPage();
    }

    @PostMapping("/uploadFile")
    public String submitFile(@RequestParam("file") MultipartFile multipartFile, Model model, Principal principal) {
        String pathToFile = userUploadedImagesFolder.getAbsolutePath() + "_" + principal.getName() + "_" + multipartFile.getOriginalFilename();
        File file = new File(pathToFile);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            LOGGER.error("Error while transferring org.springframework.web.multipart.MultipartFile to java.io.File ", e);
            model.addAttribute("error", wrongFileFormatMessage);
            return Pages.UPLOAD_FILE_PAGE.getPage();
        }
        String textFromRecognitionService = recognitionService.resolveImage(file);
        if (textFromRecognitionService.equals(emptyResponseMessage)) {
            model.addAttribute("error", cannotRecogniseCharactersMessage);
        } else model.addAttribute("recognisedText", textFromRecognitionService);

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            LOGGER.error("Cannot delete file " + file.getPath(), e);
        }
        return Pages.UPLOAD_FILE_PAGE.getPage();
    }
}
