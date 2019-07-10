package com.rubinskyi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

@Service
public class FileImportationServiceImpl implements FileImportationService {
    private final DictionaryService dictionaryService;

    @Autowired
    public FileImportationServiceImpl(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public String getTodayTxtFile(User user) {
        List<DictionaryElement> dictionaryElements = dictionaryService.getTodaysDictionaryElements(user);

        return makeSingleStringWithFiles(dictionaryElements);
    }

    public String getAllTimeTxtFile(User user) {
        List<DictionaryElement> allTimeDictionaryElements = dictionaryService.getAllDictionaryElements(user);

        return makeSingleStringWithFiles(allTimeDictionaryElements);
    }

    private String makeSingleStringWithFiles(List<DictionaryElement> dictionaryElements) {
        StringBuilder content = new StringBuilder();
        dictionaryElements.forEach(dictionaryElement -> content.append(dictionaryElement
                .importer()
                .getDictionaryElementAsString()));

        return content.toString();
    }
}
