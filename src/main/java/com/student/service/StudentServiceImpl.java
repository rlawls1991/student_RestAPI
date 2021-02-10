package com.student.service;

import com.student.domain.Student;
import com.student.domain.StudentDto;
import com.student.domain.StudentRepository;
import com.student.domain.StudentResource;
import com.student.domain.controller.StudentController;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity createStudent(final StudentDto studentDto) {
        Student student = modelMapper.map(studentDto, Student.class);
        student.setUseInfo(true);

        Student newStudent = this.studentRepository.save(student);
        var selfLinkBuilder = linkTo(StudentController.class).slash(newStudent.getId());
        URI createdUri = selfLinkBuilder.toUri();
        StudentResource studentResource = new StudentResource(newStudent);
        studentResource.add(linkTo(StudentController.class).withRel("query-students"));
        studentResource.add(selfLinkBuilder.withRel("update-student"));
        studentResource.add(Link.of("/docs/index.html#resources-student-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(studentResource);
    }

    @Override
    public ResponseEntity queryStudent(final Pageable pageable, final PagedResourcesAssembler<Student> assembler) {

        Page<Student> page = this.studentRepository.findAll(pageable);

        var pageResource = assembler.toModel(page, e -> new StudentResource(e));
        pageResource.add(Link.of("/docs/index.html#resources-student-list").withRel("profile"));

        return ResponseEntity.ok(pageResource);
    }

    @Override
    public ResponseEntity getStudent(final int id) {
        Optional<Student> optionalStudent = this.studentRepository.findById(id);

        if(optionalStudent.isPresent()){
            return ResponseEntity.notFound().build();
        }

        StudentResource studentResource = new StudentResource(optionalStudent.get());
        studentResource.add(Link.of("/docs/index.html#resources-student-get").withRel("profile"));

        return ResponseEntity.ok(studentResource);
    }

    @Override
    public ResponseEntity updateStudent(final int id, final StudentDto studentDto) {
        Optional<Student> optionalStudent = this.studentRepository.findById(id);

        if(optionalStudent.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Student existingStudent = new Student();
        this.modelMapper.map(studentDto, existingStudent);
        existingStudent = this.studentRepository.save(existingStudent);
        StudentResource studentResource = new StudentResource(existingStudent);
        studentResource.add(Link.of("/docs/index.html#resources-student-update").withRel("profile"));

        return ResponseEntity.ok(studentResource);
    }

    @Override
    public ResponseEntity deleteStudent(final int id) {
        Optional<Student> optionalStudent = this.studentRepository.findById(id);

        if(optionalStudent.isPresent()){
            return ResponseEntity.notFound().build();
        }

        studentRepository.delete(optionalStudent.get());

        StudentResource studentResource = new StudentResource(optionalStudent.get());
        studentResource.add(Link.of("/docs/index.html#resources-student-delete").withRel("profile"));

        return ResponseEntity.ok(studentResource);
    }
}
