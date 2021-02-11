package com.student.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.student.domain.QStudent;
import com.student.domain.Student;
import com.student.domain.dto.StudentDto;
import com.student.domain.subject.QSubject;
import com.student.domain.subject.SubjectKindStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public StudentRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Student> searchStudent(StudentDto dto) {
        QStudent student = QStudent.student;

        return query
                .select(student)
                .from(student)
                .where(nameEq(dto.getName()), emailEq(dto.getEmail()))
                .fetch();
    }

    private BooleanExpression nameEq(String name) {
        return QStudent.student.name.eq(name);
    }

    private BooleanExpression emailEq(String email) {
        return QStudent.student.email.eq(email);
    }

    private BooleanExpression subjectKindStatusEq(SubjectKindStatus status) {
        return QSubject.subject.subjectKindStatus.eq(status);
    }
}
