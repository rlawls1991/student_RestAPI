package com.student.domain.subject.english;

import com.student.domain.subject.dto.EnglishInputDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component
public class EnglishValidator {

    public void validate(final EnglishInputDto englishInputDto, final Errors errors) {
        checkSubjectKindStatus(englishInputDto, errors);
        checkEnglishStatus(englishInputDto, errors);
    }

    private void checkSubjectKindStatus(final EnglishInputDto englishInputDto, final Errors errors) {
        if (englishInputDto.getSubjectKindStatus().getValue() == null) {
            errors.rejectValue("subjectKindStatus", "wrongValue", "SubjectKindStatus is not null");
        }
    }

    private void checkEnglishStatus(final EnglishInputDto englishInputDto, final Errors errors) {
        if (englishInputDto.getSubjectKindStatus().getValue() == null) {
            errors.rejectValue("englishStatus", "wrongValue", "EnglishStatus is not null");
        }
    }
}
