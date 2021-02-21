package com.student.config;

import com.student.domain.subject.english.EnglishConverter;
import com.student.domain.subject.SubjectConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new EnglishConverter());
        registry.addConverter(new SubjectConverter());
    }
}
