package com.rubinskyi.service.impl;

import java.util.List;

import com.rubinskyi.service.DictionaryService;
import com.rubinskyi.service.FileImportationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TextFileImportationService implements FileImportationService {
    private final DictionaryService dictionaryService;

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
