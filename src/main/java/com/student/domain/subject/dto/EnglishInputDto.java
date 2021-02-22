package com.student.domain.subject.dto;

import com.student.domain.subject.english.EnglishStatus;
import com.student.domain.subject.SubjectKindStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnglishInputDto {

    @NotEmpty
    private String grade;
    @Min(0)
    @Max(100)
    private int score;

    @NotEmpty
    private String classCode;
    @NotEmpty
    private String professorName;

    @Enumerated(EnumType.STRING)
    private SubjectKindStatus subjectKindStatus;

    @Enumerated(EnumType.STRING)
    private EnglishStatus englishStatus;
}
