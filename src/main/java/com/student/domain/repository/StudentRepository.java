package com.student.domain.repository;

import com.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom {

}
