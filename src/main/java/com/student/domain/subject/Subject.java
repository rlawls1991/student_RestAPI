package com.student.domain.subject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.student.domain.Student;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Subject {

    @Id @GeneratedValue
    @Column(name ="subject_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubjectKindStatus subjectKindStatus;

    private String grade;
    private int score;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}
