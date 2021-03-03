package com.student.domain;


import com.student.domain.exception.NotFindSubjectException;
import com.student.domain.subject.Subject;
import com.student.domain.subject.SubjectKindStatus;
import com.student.domain.subject.english.EnglishStatus;
import lombok.*;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Setter
public class Student {
    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private Long id;

    @Column(nullable = true, length = 30)
    private String name;
    private int age;
    @Column(nullable = true, length = 30)
    private String phone;
    @Column(nullable = true, length = 100)
    private String email;
    @Column(nullable = true, length = 100)
    private String address;
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Subject> grades = new ArrayList<>();

    public void createSetting() {
        this.createDateTime = LocalDateTime.now();
    }

    public void addSubject(final Subject subject) {
        if (grades == null) {
            grades = new ArrayList<>();
        }
        this.grades.add(subject);
    }

    public void removeSubject(final SubjectKindStatus subjectKindStatus) {
        if (grades == null) {
            return;
        }

        for (int i = 0; i < grades.size(); i++) {
            removeTargetSubject(i, subjectKindStatus);
        }
    }

    private void removeTargetSubject(int index, final SubjectKindStatus subjectKindStatus) {
        if (this.grades.get(index).getSubjectKindStatus().getKey().equals(subjectKindStatus.getKey())) {
            grades.remove(index);
        }
    }
}
