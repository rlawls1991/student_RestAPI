package com.student.domain.subject.service;

import com.student.domain.Student;
import com.student.domain.dto.StudentDto;
import com.student.domain.repository.StudentRepository;
import com.student.domain.subject.SubjectKindStatus;
import com.student.domain.subject.dto.EnglishInputDto;
import com.student.domain.subject.english.English;
import com.student.domain.subject.english.EnglishStatus;
import com.student.domain.subject.repository.SubjectRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnglishServiceImpl implements EnglishService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public StudentDto createEnglish(final Long id, final EnglishInputDto dto) {
        Student student = studentRepository.findByEntity(id);
        English english = new English();

        english.setGrade(dto.getGrade());
        english.setScore(dto.getScore());
        english.setClassCode(dto.getClassCode());
        english.setProfessorName(dto.getProfessorName());
        english.setSubjectKindStatus(dto.getSubjectKindStatus());
        english.setEnglishStatus(dto.getEnglishStatus());

        student.addSubject(english);

        StudentDto returnDto = modelMapper.map(student, StudentDto.class);

        return returnDto;
    }

    @Override
    @Transactional
    public StudentDto removeEnglish(final Long id, final SubjectKindStatus subjectKindStatus) {
        Student student = studentRepository.findByEntity(id);

        student.removeSubject(subjectKindStatus);

        return modelMapper.map(student, StudentDto.class);
    }
}
