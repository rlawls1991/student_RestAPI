package com.student.domain.subject.english;

import com.student.config.EnumModel;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum EnglishStatus implements EnumModel {
    //대화 , 이론
    TALKING("talking"), GRAMMAR("grammar");

    private final String value;

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
