package com.student.domain.subject.service;

import com.student.domain.Student;
import com.student.domain.dto.StudentDto;
import com.student.domain.subject.SubjectKindStatus;
import com.student.domain.subject.dto.EnglishInputDto;

public interface EnglishService {

    /**
     * 영어과목 점수 설정
     *
     * @param id
     * @param englishInputDto
     * @return
     */
    public StudentDto createEnglish(final Long id, final EnglishInputDto englishInputDto);

    /**
     * 중간고사 또는 기말고사 점수 삭제
     *
     * @param id
     * @param subjectKindStatus
     * @return
     */
    public StudentDto removeEnglish(final Long id, final SubjectKindStatus subjectKindStatus);
}
