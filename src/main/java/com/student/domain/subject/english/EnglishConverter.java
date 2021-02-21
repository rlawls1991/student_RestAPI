package com.student.domain.subject.english;

import org.springframework.core.convert.converter.Converter;

public class EnglishConverter implements Converter<String, EnglishStatus> {

    @Override
    public EnglishStatus convert(String english) {
        return EnglishStatus.valueOf(english);
    }
}
