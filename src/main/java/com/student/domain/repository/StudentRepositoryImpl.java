package com.student.domain.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.student.domain.QStudent;
import com.student.domain.Student;
import com.student.domain.dto.SearchDto;
import com.student.domain.dto.StudentDto;
import com.student.domain.subject.QSubject;
import com.student.domain.subject.SubjectKindStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepositoryCustom {
    private final JPAQueryFactory query;
    private final EntityManager em;

    public Student findByStudent(StudentDto dto) {
        QStudent student = QStudent.student;

        return query
                .selectFrom(student)
                .where(nameEq(dto.getName())
                        , emailEq(dto.getEmail())
                        , phoneEq(dto.getPhone())
                        , ageEq(dto.getAge())
                )
                .fetchOne();
    }

    public void saveStudent(Student student) {
        em.persist(student);
    }

    public Page<Student> findAll(SearchDto dto, Pageable pageable) {
        QStudent student = QStudent.student;
        QSubject subject = QSubject.subject;

        QueryResults<Student> result = query
                .selectFrom(student)
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

    public void deleteById(Integer id) {
        QStudent student = QStudent.student;

        query.delete(student)
                .where(idEq(id))
                .execute();
    }

    public Student findById(Integer id) {
        QStudent student = QStudent.student;

        return query
                .select(student)
                .from(student)
                .where(idEq(id))
                .fetchOne();
    }

    private BooleanExpression nameEq(String name) {
        if (name == null) {
            return null;
        }
        return QStudent.student.name.eq(name);
    }

    private BooleanExpression emailEq(String email) {
        if (email == null) {
            return null;
        }
        return QStudent.student.email.eq(email);
    }

    private BooleanExpression phoneEq(String phone) {
        if (phone == null) {
            return null;
        }
        return QStudent.student.phone.eq(phone);
    }

    private BooleanExpression ageEq(int age) {
        if (age == 0) {
            return null;
        }

        return QStudent.student.age.eq(age);
    }

    private BooleanExpression idEq(Integer id) {
        if (id == 0) {
            return null;
        }
        return QStudent.student.id.eq(id);
    }

    private BooleanExpression subjectKindStatusEq(SubjectKindStatus status) {
        return QSubject.subject.subjectKindStatus.eq(status);
    }

}
