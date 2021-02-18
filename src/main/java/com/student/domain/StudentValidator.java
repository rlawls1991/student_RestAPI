package com.student.domain;

import com.student.domain.dto.StudentInputDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class StudentValidator {

    public void validate(final StudentInputDto studentInput, final Errors errors) {
        if(studentInput.getName().length() > 10){
            errors.rejectValue("name", "wrongValue", "10 is greater than nameLength.");
        }
    }
}
