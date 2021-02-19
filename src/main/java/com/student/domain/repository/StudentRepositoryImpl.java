package com.student.domain.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.student.domain.dto.QStudentDto;
import com.student.domain.dto.SearchDto;
import com.student.domain.dto.StudentDto;
import com.student.domain.dto.StudentInputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.student.domain.QStudent.student;
import static com.student.domain.subject.QSubject.subject;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    private final JPAQueryFactory query;

    public StudentDto findByStudent(final StudentInputDto studentInput) {
        return query
                .select(new QStudentDto(
                        student.id
                        , student.name
                        , student.age
                        , student.phone
                        , student.email
                        , student.address
                        , student.createDateTime))
                .from(student)
                .where(nameEq(studentInput.getName())
                        , emailEq(studentInput.getEmail())
                        , phoneEq(studentInput.getPhone())
                        , ageEq(studentInput.getAge())
                )
                .fetchOne();
    }

    public StudentDto findByStudent(final Long id) {
        return query
                .select(new QStudentDto(
                        student.id
                        , student.name
                        , student.age
                        , student.phone
                        , student.email
                        , student.address
                        , student.createDateTime))
                .from(student)
                .where(idEq(id))
                .fetchOne();
    }

    public Page<StudentDto> findAll(final SearchDto dto, final Pageable pageable) {
        QueryResults<StudentDto> result = query
                .select( new QStudentDto(
                        student.id
                        , student.name
                        , student.age
                        , student.phone
                        , student.email
                        , student.address
                        , student.grades
                        , student.createDateTime
                ))
                .from(student)
                .leftJoin(student.grades, subject)
                .where(nameEq(dto.getName())
                        , emailEq(dto.getEmail())
                        , phoneEq(dto.getPhone())
                        , ageEq(dto.getAge())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression nameEq(final String name) {
        if (name == null) {
            return null;
        }
        return student.name.eq(name);
    }

    private BooleanExpression emailEq(final String email) {
        if (email == null) {
            return null;
        }
        return student.email.eq(email);
    }

    private BooleanExpression phoneEq(final String phone) {
        if (phone == null) {
            return null;
        }
        return student.phone.eq(phone);
    }

    private BooleanExpression ageEq(final int age) {
        if (age == 0) {
            return null;
        }

        return student.age.eq(age);
    }

    private BooleanExpression idEq(final Long id) {
        if (id == 0) {
            return null;
        }
        return student.id.eq(id);
    }
}
