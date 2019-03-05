package com.nixsolutions.validation.dictionaryElement;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nixsolutions.service.DictionaryService;

@Component
public class WordUniqValidator implements ConstraintValidator<WordUniq, String> {

    private final DictionaryService dictionaryService;

    @Autowired
    public WordUniqValidator(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Override
    public void initialize(WordUniq wordUniq) {
    }

    @Override
    public boolean isValid(String word, ConstraintValidatorContext constraintValidatorContext) {
        return dictionaryService.findByWord(word) == null;
    }

}
