package com.nixsolutions.service.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nixsolutions.entity.DictionaryElement;
import com.nixsolutions.pojo.api.TutorCard;

// TODO rename all this services
@Service
public class ApiService {
    private final TranslationService translationService;
    private final ExamplesAndTranscriptionService examplesAndTranscriptionService;

    public ApiService(@Autowired TranslationService translationService, ExamplesAndTranscriptionService examplesAndTranscriptionService) {
        this.translationService = translationService;
        this.examplesAndTranscriptionService = examplesAndTranscriptionService;
    }

    public DictionaryElement getDictionaryElementFromApi(String wordInEnglish) {
        String translation = translationService.getTranslationFromApi(wordInEnglish);
        List<TutorCard> tutorCards = examplesAndTranscriptionService.getTranslationFromApi(wordInEnglish);
        // TODO move this to some service
        String transcription = tutorCards.get(0).getTranscription();
        String example = tutorCards.get(0).getExamples();

        return new DictionaryElement.Builder(wordInEnglish, translation).transcription(transcription).example(example).build();
    }
}
