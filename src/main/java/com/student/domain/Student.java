package com.student.domain;


import com.student.domain.subject.Subject;
import lombok.*;
import org.apache.tomcat.jni.Local;

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
    private Integer id;

    private String name;
    private int age;
    private String phone;
    private String email;
    private String address;
    private boolean useInfo;
    private LocalDateTime createDateTime;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Subject> grades = new ArrayList<>();

    public void createSetting() {
        this.createDateTime = LocalDateTime.now();
        this.useInfo = true;
    }

    public void deleteSetting() {
        this.useInfo = false;
    }
}
