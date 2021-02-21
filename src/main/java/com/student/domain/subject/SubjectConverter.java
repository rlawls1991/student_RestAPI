package com.student.domain.subject;

import org.springframework.core.convert.converter.Converter;

public class SubjectConverter implements Converter<String, SubjectKindStatus>{

    @Override
    public SubjectKindStatus convert(String subjectKind) {
        return SubjectKindStatus.valueOf(subjectKind);
    }
}
