package com.student.service;

import com.student.domain.Student;
import com.student.domain.dto.SearchDto;
import com.student.domain.dto.StudentDto;
import com.student.domain.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Student createStudent(final StudentDto studentDto) {
        Student student = modelMapper.map(studentDto, Student.class);
        student.createSetting();
        return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student searchStudent(final StudentDto studentDto){
        return studentRepository.findByStudent(studentDto);
    }

    @Override
    public Page<Student> queryStudent(final Pageable pageable, SearchDto searchDto) {
        return studentRepository.findAll(searchDto, pageable);
    }

    @Override
    public Student getStudent(final Integer id) {
        return studentRepository.findById(id);
    }

    @Override
    @Transactional
    public Student updateStudent(final Integer id, final StudentDto dto) {
        Student student = studentRepository.findById(id);

        student.setAge(dto.getAge());
        student.setName(dto.getName());
        student.setAddress(dto.getAddress());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());

        return studentRepository.saveAndFlush(student);
    }

    @Override
    @Transactional
    public void deleteStudent(final Integer id) {
        Student student = studentRepository.findById(id);
        studentRepository.delete(student);
    }
}
