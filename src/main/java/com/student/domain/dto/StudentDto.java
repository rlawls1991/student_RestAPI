package com.student.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.student.domain.subject.Subject;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
    private int age;
    private String phone;
    private String email;
    private String address;

    private List<Subject> grades = new ArrayList<>();
    private LocalDateTime createDateTime;

    @QueryProjection
    public StudentDto(Long id, String name, int age, String phone, String email, String address, LocalDateTime createDateTime) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.createDateTime = createDateTime;
    }

    @QueryProjection
    public StudentDto(Long id, String name, int age, String phone, String email, String address, List<Subject> grades, LocalDateTime createDateTime) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.grades = grades;
        this.createDateTime = createDateTime;
    }
}