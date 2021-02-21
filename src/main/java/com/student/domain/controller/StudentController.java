package com.student.domain.controller;


import com.student.Error.ApiResponseMessage;
import com.student.Error.ErrorsResource;
import com.student.domain.StudentResource;
import com.student.domain.StudentValidator;
import com.student.domain.dto.SearchDto;
import com.student.domain.dto.StudentDto;
import com.student.domain.dto.StudentInputDto;
import com.student.domain.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@Controller
@RequestMapping(value = "/api/student", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class StudentController {
    private final StudentValidator studentValidator;
    private final StudentService studentService;

    /**
     * 학생 생성 Controller
     *
     * @param studentInput
     * @param errors
     * @return
     */
    @PostMapping
    public ResponseEntity createStudentResponse(@Valid @RequestBody StudentInputDto studentInput, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        studentValidator.validate(studentInput, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        StudentDto searchStudent = studentService.searchStudent(studentInput);

        if (searchStudent != null) {
            ApiResponseMessage message = new ApiResponseMessage("Fail", "overlapData", "", "");
            return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.BAD_REQUEST);
        }

        StudentDto student = studentService.createStudent(studentInput);

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
    public ResponseEntity getStudent(@PathVariable Long id) {
        StudentDto student = studentService.getStudent(id);

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
     * @param
     * @param searchDto
     * @return
     */
    @GetMapping
    public ResponseEntity queryStudents(@Valid SearchDto searchDto, Pageable pageable, PagedResourcesAssembler<StudentDto> assembler) {

        Page<StudentDto> studentPages = studentService.queryStudent(pageable, searchDto);

        var pageResource = assembler.toModel(studentPages, e -> new StudentResource(e));
        pageResource.add(Link.of("/docs/index.html#resources-students-list").withRel("profile"));

        return ResponseEntity.ok(pageResource);
    }

    /**
     * 학생 정보 수정
     *
     * @param id
     * @param studentInput
     * @param errors
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity updateStudent(@PathVariable Long id,
                                        @RequestBody @Valid StudentInputDto studentInput, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        studentValidator.validate(studentInput, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        StudentDto student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        StudentDto existingStudent = studentService.updateStudent(id, studentInput);

        StudentResource studentResource = new StudentResource(existingStudent);
        studentResource.add(Link.of("/docs/index.html#resources-student-update").withRel("profile"));

        return ResponseEntity.ok(studentResource);
    }

    /**
     * 학생정보 삭제
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        StudentDto student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        studentService.deleteStudent(id);
        StudentResource studentResource = new StudentResource(student);
        studentResource.add(Link.of("/docs/index.html#resources-student-delete").withRel("profile"));

        return ResponseEntity.ok(studentResource);
    }

    private ResponseEntity<?> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
}
