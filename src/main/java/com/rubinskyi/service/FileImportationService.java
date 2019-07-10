package com.rubinskyi.service;

import com.rubinskyi.entity.User;

public interface FileImportationService {

    String getTodayTxtFile(User user);

    String getAllTimeTxtFile(User user);
}
