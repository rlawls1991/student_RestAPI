package com.student.domain.subject;

import com.student.config.EnumModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SubjectKindStatus implements EnumModel {
    //중간고사, 기말고사
    MIDTERM("midterm"),
    FINAL("final");

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
