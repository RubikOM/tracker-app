package com.rubinskyi.service;

import java.util.List;

import com.rubinskyi.entity.DictionaryElement;

public interface DictionaryElementConsolidatorService {

    DictionaryElement consolidateDictionaryElements(List<DictionaryElement> dictionaryElementList);
}
