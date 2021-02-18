package com.student.domain.subject.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.student.domain.QStudent;
import com.student.domain.subject.QSubject;
import com.student.domain.subject.Subject;
import com.student.domain.subject.SubjectKindStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class SubjectRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public SubjectRepository(EntityManager em){
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Subject> searchSubject() {
        QSubject subject = QSubject.subject;

        return query
                .select(subject)
                .from(subject)
                .fetch();
    }

    private BooleanExpression nameEq(String name) {
        return QStudent.student.name.eq(name);
    }

    private BooleanExpression studentIdEq(Long id) {
        return QStudent.student.id.eq(id);
    }

    private BooleanExpression subjectKindStatusEq(SubjectKindStatus status) {
        return QSubject.subject.subjectKindStatus.eq(status);
    }
}
