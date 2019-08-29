package com.rubinskyi.bean.mapper;

import com.rubinskyi.entity.DictionaryElement;
import com.rubinskyi.pojo.lingvo.ComprehensiveElementLingvo;

public interface ComprehensiveElementMapper{

    DictionaryElement comprehensiveElementToDictionaryElement(ComprehensiveElementLingvo comprehensiveElementLingvo);
}
