package com.rubinskyi.service.impl;

import java.util.List;

import com.rubinskyi.service.DictionaryElementService;
import com.rubinskyi.service.FileImportationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

@Service
@RequiredArgsConstructor
public class TextFileImportationService implements FileImportationService {
    private final DictionaryElementService dictionaryElementService;

    public String getTodayTxtFile(User user) {
        List<DictionaryElement> dictionaryElements = dictionaryElementService.getTodaysDictionaryElements(user);

        return makeSingleStringWithFiles(dictionaryElements);
    }

    public String getAllTimeTxtFile(User user) {
        List<DictionaryElement> allTimeDictionaryElements = dictionaryElementService.getAllDictionaryElements(user);

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
