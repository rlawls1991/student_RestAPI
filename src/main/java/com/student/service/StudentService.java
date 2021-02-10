package com.student.service;


import com.student.domain.Student;
import com.student.domain.StudentDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;

public interface StudentService {

    /**
     * 학생을 생성하는 서비스
     * @return
     */
    public ResponseEntity createStudent(final StudentDto studentDto);

    /**
     * 학생들을 조회하는 서비스
     * @return
     */
    public ResponseEntity queryStudent(final Pageable pageable, final PagedResourcesAssembler<Student> assembler);

    /**
     * 학생 한명을 조회하는 서비스
     * @return
     */
    public ResponseEntity getStudent(final int id);

    /**
     * 학생 정보를 수정하는 서비스
     * @return
     */
    public ResponseEntity updateStudent(final int id, final StudentDto studentDto);

    /**
     * 학생을 삭제하는 서비스(Y->N으로 수정)
     * @return
     */
    public ResponseEntity deleteStudent(final int id);
}
