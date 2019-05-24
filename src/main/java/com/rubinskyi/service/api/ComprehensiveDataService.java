package com.rubinskyi.service.api;

import java.util.Map;

import com.rubinskyi.entity.User;

public interface ComprehensiveDataService {

    public Map obtainDataFromApi(String wordInEnglish, User user);
}
