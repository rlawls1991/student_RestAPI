package com.student.service;


import com.student.domain.Student;
import com.student.domain.dto.SearchDto;
import com.student.domain.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface StudentService {

    /**
     * 학생을 생성하는 서비스
     *
     * @return
     */
    public Student createStudent(final StudentDto studentDto);


    /**
     * 학생을 조회하는 서비스
     * @param studentDto
     * @return
     */
    public Student searchStudent(final StudentDto studentDto);

    /**
     * 학생들을 조회하는 서비스
     *
     * @return
     */
    public Page<Student> queryStudent(final Pageable pageable, final SearchDto searchDto);

    /**
     * 학생 한명을 조회하는 서비스
     *
     * @return
     */
    public Student getStudent(final Integer id);

    /**
     * 학생 정보를 수정하는 서비스
     *
     * @return
     */
    public Student updateStudent(final Integer id, final StudentDto studentDto);

    /**
     * 학생을 삭제하는 서비스(Y->N으로 수정)
     *
     * @return
     */
    public Student deleteStudent(final Integer id);
}
