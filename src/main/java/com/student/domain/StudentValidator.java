package com.student.domain;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class StudentValidator {

    public void validate(final StudentDto studentDto, final Errors errors) {
        checkCreateDateTime(studentDto.getCreateDateTime());
    }

    private void checkCreateDateTime(final LocalDateTime paramLocalDate) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (currentDateTime.isBefore(paramLocalDate)) {
            throw new RuntimeException("CreateDateTime은 현재시간보다 이전시간이어야 합니다.");
        }
    }
}
