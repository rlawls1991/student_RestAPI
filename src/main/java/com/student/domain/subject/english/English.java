package com.student.domain.subject.english;


import com.student.domain.subject.Subject;
import com.student.domain.subject.english.EnglishStatus;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("english")
@Getter
public class English extends Subject {
    private String classCode;
    private String professorName;

    private EnglishStatus status;
}
