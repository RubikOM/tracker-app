package com.rubinskyi.service.api;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.entity.User;

public interface ComprehensiveDataService {

    DictionaryElement obtainDataFromApi(String wordInEnglish, User user);
}
