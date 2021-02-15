package com.student.domain.controller;


import com.student.Error.ApiResponseMessage;
import com.student.Error.ErrorsResource;
import com.student.domain.Student;
import com.student.domain.StudentResource;
import com.student.domain.StudentValidator;
import com.student.domain.dto.SearchDto;
import com.student.domain.dto.StudentDto;
import com.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@Controller
@RequestMapping(value = "/api/student", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class StudentController {
    private final StudentValidator studentValidator;
    private final StudentService studentService;
    private final ModelMapper modelMapper;

    /**
     * 학생 생성 Controller
     *
     * @param studentDto
     * @param errors
     * @return
     */
    @PostMapping
    public @ResponseBody
    ResponseEntity createStudent(@Valid @RequestBody StudentDto studentDto, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        studentValidator.validate(studentDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Student searchStudent = studentService.searchStudent(studentDto);

        if (searchStudent != null) {
            ApiResponseMessage message = new ApiResponseMessage("Fail", "overlapData", "", "");
            return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }

        Student student = studentService.createStudent(studentDto);

        var selfLinkBuilder = linkTo(StudentController.class).slash(student.getId());
        URI createdUri = selfLinkBuilder.toUri();
        StudentResource studentResource = new StudentResource(student);
        studentResource.add(linkTo(StudentController.class).withRel("query-students"));
        studentResource.add(selfLinkBuilder.withRel("update-student"));
        studentResource.add(Link.of("/docs/index.html#resources-student-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(studentResource);
    }

    /**
     * 학생 한명 조회
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity getStudent(@PathVariable Integer id) {
        Student student = studentService.getStudent(id);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        StudentResource studentResource = new StudentResource(student);
        studentResource.add(Link.of("/docs/index.html#resources-student-get").withRel("profile"));

        return ResponseEntity.ok(studentResource);
    }

    /**
     * 학생들의 정보 조회(페이징)
     *
     * @param pageable
     * @param assembler
     * @param searchDto
     * @return
     */
    @GetMapping
    public ResponseEntity queryStudents(@Valid SearchDto searchDto, Pageable pageable, PagedResourcesAssembler<Student> assembler) {

        Page<Student> studentPages = studentService.queryStudent(pageable, searchDto);

        var pageResource = assembler.toModel(studentPages, e -> new StudentResource(e));
        pageResource.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));
        pageResource.add(linkTo(StudentController.class).withRel("create-event"));

        return ResponseEntity.ok(pageResource);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity updateStudent(@PathVariable Integer id,
                                 @Valid StudentDto studentDto, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        Student student = studentService.getStudent(id);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        Student existingStudent = studentService.updateStudent(id, studentDto);
        StudentResource studentResource = new StudentResource(existingStudent);
        studentResource.add(Link.of("/docs/index.html#resources-student-update").withRel("profile"));

        return ResponseEntity.ok(studentResource);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody
    ResponseEntity deleteStudent(@PathVariable Integer id, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        Student deleteStudent = studentService.deleteStudent(id);
        StudentResource studentResource = new StudentResource(deleteStudent);
        studentResource.add(Link.of("/docs/index.html#resources-student-delete").withRel("profile"));

        return ResponseEntity.ok(studentResource);
    }

    private ResponseEntity<?> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
}
