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

        Student saveStudent = studentRepository.saveAndFlush(student);
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
    public StudentDto getStudent(final Integer id) {
        Optional<Student> student = studentRepository.findById(Long.valueOf(id));

        if (student.isPresent()) {
            return null;
        }

        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    @Transactional
    public StudentDto updateStudent(final Integer id, final StudentInputDto studentInput) {
        Optional<Student> student = studentRepository.findById(id.longValue());

        student.get().setAge(studentInput.getAge());
        student.get().setName(studentInput.getName());
        student.get().setAddress(studentInput.getAddress());
        student.get().setEmail(studentInput.getEmail());
        student.get().setPhone(studentInput.getPhone());


        return modelMapper.map(studentRepository.save(student.get()), StudentDto.class);
    }

    @Override
    @Transactional
    public void deleteStudent(final Integer id) {
        Optional<Student> student = studentRepository.findById(id.longValue());
        studentRepository.delete(student.get());
    }
}
