package com.student.domain.subject.repository;

import com.student.domain.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long>, SubjectRepositoryCustom {
}
