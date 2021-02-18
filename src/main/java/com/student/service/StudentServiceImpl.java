package com.student.service;

import com.student.domain.Student;
import com.student.domain.dto.SearchDto;
import com.student.domain.dto.StudentDto;
import com.student.domain.dto.StudentInputDto;
import com.student.domain.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public StudentDto createStudent(final StudentInputDto studentInput) {
        Student student = modelMapper.map(studentInput, Student.class);
        student.createSetting();
        Student saveStudent = studentRepository.save(student);

        return modelMapper.map(saveStudent, StudentDto.class);
    }

    @Override
    public StudentDto searchStudent(final StudentInputDto studentInput) {
        return studentRepository.findByStudent(studentInput);
    }

    @Override
    public Page<StudentDto> queryStudent(final Pageable pageable, SearchDto searchDto) {
        return studentRepository.findAll(searchDto, pageable);
    }

    @Override
    public StudentDto getStudent(final Long id) {
        StudentDto student = studentRepository.findByStudent(id);

        if (student == null) {
            return null;
        }

        return student;
    }

    @Override
    @Transactional
    public StudentDto updateStudent(final Long id, final StudentInputDto studentInput) {
        StudentDto studentDto = studentRepository.findByStudent(id);

        studentDto.setAge(studentInput.getAge());
        studentDto.setName(studentInput.getName());
        studentDto.setAddress(studentInput.getAddress());
        studentDto.setEmail(studentInput.getEmail());
        studentDto.setPhone(studentInput.getPhone());

        return studentDto;
    }

    @Override
    @Transactional
    public void deleteStudent(final Long id) {
        studentRepository.deleteById(id);
    }
}
