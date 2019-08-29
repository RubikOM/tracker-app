package com.rubinskyi.service.outerApi.impl;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.service.outerApi.FileTranslationService;
import com.rubinskyi.service.outerApi.MultiWordTranslationService;
import com.rubinskyi.service.outerApi.SuggestedTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileTranslationServiceImpl implements FileTranslationService {
    private final MultiWordTranslationService multiWordTranslationService;
    private final SuggestedTranslationService suggestedTranslationService;

    @Override
    @Transactional(readOnly=true)
    public Map<String, Object> getTranslations(String text, String username) {
        Map<String, Object> result = new HashMap<>();

        String russian = multiWordTranslationService.translateTextToRussian(text);
        List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(text, username);
        result.put("russian", russian);
        result.put("suggestedElements", suggestedElements);

        return result;
    }
}
