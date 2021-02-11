package com.student.domain.subject;


import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("english")
@Getter
public class English extends Subject{
    private String classCode;
    private String professorName;

    private EnglishStatus status;
}
