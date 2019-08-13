package com.rubinskyi.controller;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.pojo.Pages;
import com.rubinskyi.service.ImageCharacterRecognitionService;
import com.rubinskyi.service.UserService;
import com.rubinskyi.service.api.MultiWordTranslationService;
import com.rubinskyi.service.api.SuggestedTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Controller
@RequestMapping("/dictionary")
@PropertySource("classpath:characterRecognition.properties")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CharacterRecognitionController {
    @Value("classpath:tessimage")
    private File userUploadedImagesFolder;
    @Value("${emptyResponse}")
    private String emptyResponseMessage;
    @Value("${wrongFileType}")
    private String wrongFileFormatMessage;
    @Value("${cannotRecogniseCharacters}")
    private String cannotRecogniseCharactersMessage;

    private final ImageCharacterRecognitionService recognitionService;
    private final MultiWordTranslationService multiWordTranslationService;
    private final SuggestedTranslationService suggestedTranslationService;
    private final UserService userService;

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
            log.error("Error while transferring org.springframework.web.multipart.MultipartFile to java.io.File ", e);
            model.addAttribute("error", wrongFileFormatMessage);
            return Pages.UPLOAD_FILE_PAGE.getPage();
        }
        String textFromRecognitionService = recognitionService.resolveImage(file);
        if (textFromRecognitionService.equals(emptyResponseMessage)) {
            model.addAttribute("error", cannotRecogniseCharactersMessage);
        } else {
            List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(textFromRecognitionService, userService.findByLogin(principal.getName()));
            String russianTranslation = multiWordTranslationService.translateTextToRussian(textFromRecognitionService);
            model.addAttribute("recognisedText", textFromRecognitionService);
            model.addAttribute("russianTranslation", russianTranslation);
            model.addAttribute("suggestedElements", suggestedElements);
        }

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            log.error("Cannot delete file " + file.getPath(), e);
        }
        return Pages.UPLOAD_FILE_PAGE.getPage();
    }
}
