package com.student.domain.subject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.student.domain.Student;
import lombok.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Subject {

    @Id @GeneratedValue
    @Column(name ="subject_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubjectKindStatus subjectKindStatus;

    @Column(nullable = true, length = 5)
    private String grade;
    private int score;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}
