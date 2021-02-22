package com.student.domain.subject.english;


import com.student.domain.subject.Subject;
import com.student.domain.subject.english.EnglishStatus;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("english")
@Getter @Setter
public class English extends Subject {
    private String classCode;
    private String professorName;
    private EnglishStatus englishStatus;
}
