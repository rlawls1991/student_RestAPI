package com.student.domain.subject;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("computer")
@Getter
public class Computer extends Subject{
    private String classCode;
    private String professorName;

    private ComputerStatus status;
}