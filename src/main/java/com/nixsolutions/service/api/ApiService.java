package com.nixsolutions.service.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.pojo.api.TutorCard;

@Service
public class ApiService {
    private final TranslationService translationService;
    private final AdditionalDataService additionalDataService;

    public ApiService(@Autowired TranslationService translationService, AdditionalDataService additionalDataService) {
        this.translationService = translationService;
        this.additionalDataService = additionalDataService;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish) {
        String translation = translationService.getTranslationFromApi(wordInEnglish);
        /*List<TutorCard> tutorCards = additionalDataService.getTranslationFromApi(wordInEnglish);
        String transcription = tutorCards.get(0).getTranscription();
        String example = tutorCards.get(0).getExamples();*/

        return new DictionaryElement.Builder(wordInEnglish, translation).transcription(transcription).example(example).build();
    }
}
