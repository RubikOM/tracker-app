package com.rubinskyi.controller;

import com.rubinskyi.pojo.Pages;
import com.rubinskyi.service.ImageCharacterRecognitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.nio.file.Paths;

@Controller
@RequestMapping("/dictionary")
public class CharacterRecognitionController {
    private final ImageCharacterRecognitionService recognitionService;
    private static final String FILE_FOLDER = "src/main/resources/tessimage";
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
    public String submitFile(@RequestParam("file") MultipartFile multipartFile, Model model) {
        String pathToFile = FILE_FOLDER + multipartFile.getOriginalFilename();
        try {
            multipartFile.transferTo(Paths.get(pathToFile));
        } catch (IOException e) {
            LOGGER.error("Error while transferring org.springframework.web.multipart.MultipartFile to java.io.File ", e);
            model.addAttribute("error", "Cannot read given file, check if it has required format");
            return Pages.UPLOAD_FILE_PAGE.getPage();
        }
        File file = new File(pathToFile);

        String textFromRecognitionService = recognitionService.resolveImage(file);
        if (textFromRecognitionService.equals("emptyMessage")) {
            model.addAttribute("recognisedText", textFromRecognitionService);
        } else model.addAttribute("error", "Cannot read given file, check if it has required format");

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            LOGGER.error("Cannot delete file " + file.getPath(), e);
        }
        return Pages.UPLOAD_FILE_PAGE.getPage();
    }
}
