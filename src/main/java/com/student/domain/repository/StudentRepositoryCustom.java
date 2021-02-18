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
     * @param studentInput
     * @return
     */
    public StudentDto findByStudent(StudentInputDto studentInput);

    /**
     * 학생 페이징 형태로 조회
     * @param dto
     * @param pageable
     * @return
     */
    public Page<StudentDto> findAll(SearchDto dto, Pageable pageable);
}
