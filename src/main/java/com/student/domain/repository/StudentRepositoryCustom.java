package com.student.domain.repository;

import com.student.domain.Student;
import com.student.domain.dto.SearchDto;
import com.student.domain.dto.StudentDto;
import com.student.domain.dto.StudentInputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentRepositoryCustom {

    /**
     * 조회조건을 기준으로 한명을 조회
     *
     * @param studentInput
     * @return
     */
    public StudentDto findByStudent(final StudentInputDto studentInput);


    /**
     * 학생정보 조회
     *
     * @return
     */
    public StudentDto findByStudent(final Long id);


    /**
     * 학생 페이징 형태로 조회
     *
     * @param dto
     * @param pageable
     * @return
     */
    public Page<StudentDto> findAll(final SearchDto dto, final Pageable pageable);

}
