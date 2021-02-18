package com.student.service;


import com.student.domain.Student;
import com.student.domain.dto.SearchDto;
import com.student.domain.dto.StudentDto;
import com.student.domain.dto.StudentInputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

    /**
     * 학생을 생성하는 서비스
     *
     * @return
     */
    public StudentDto createStudent(final StudentInputDto studentInput);


    /**
     * 학생을 조회하는 서비스
     * @param studentInput
     * @return
     */
    public StudentDto searchStudent(final StudentInputDto studentInput);

    /**
     * 학생들을 조회하는 서비스
     *
     * @return
     */
    public Page<StudentDto> queryStudent(final Pageable pageable, final SearchDto searchDto);

    /**
     * 학생 한명을 조회하는 서비스
     *
     * @return
     */
    public StudentDto getStudent(final Long id);

    /**
     * 학생 정보를 수정하는 서비스
     *
     * @return
     */
    public StudentDto updateStudent(final Long id, final StudentInputDto studentInput);

    /**
     * 학생을 삭제하는 서비스(Y->N으로 수정)
     *
     * @return
     */
    public void deleteStudent(final Long id);
}
