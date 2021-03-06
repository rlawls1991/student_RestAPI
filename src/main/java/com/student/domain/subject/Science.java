package com.student.domain.subject;

import com.student.domain.subject.english.EnglishStatus;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("science")
@Getter
public class Science extends Subject{
    private String classCode;
    private String professorName;

    private EnglishStatus status;
}
