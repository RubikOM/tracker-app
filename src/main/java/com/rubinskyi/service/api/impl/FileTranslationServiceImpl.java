package com.rubinskyi.service.api.impl;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.service.api.FileTranslationService;
import com.rubinskyi.service.api.MultiWordTranslationService;
import com.rubinskyi.service.api.SuggestedTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileTranslationServiceImpl implements FileTranslationService {
    private final MultiWordTranslationService multiWordTranslationService;
    private final SuggestedTranslationService suggestedTranslationService;

    @Override
    public Map<String, Object> getTranslations(String text, String username) {
        Map<String, Object> result = new HashMap<>();

        String russian = multiWordTranslationService.translateTextToRussian(text);
        List<DictionaryElement> suggestedElements = suggestedTranslationService.getSuggestedElements(text, username);
        result.put("russian", russian);
        result.put("suggestedElements", suggestedElements);

        return result;
    }
}
