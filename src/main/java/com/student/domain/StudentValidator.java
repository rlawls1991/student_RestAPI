package com.student.domain;

import com.student.domain.dto.StudentDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class StudentValidator {

    public void validate(final StudentDto studentDto, final Errors errors) {

    }
}
