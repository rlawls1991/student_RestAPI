package com.student.domain.controller;


import com.student.Error.ErrorsResource;
import com.student.domain.Student;
import com.student.domain.StudentValidator;
import com.student.domain.dto.StudentDto;
import com.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
     * @param studentDto
     * @param errors
     * @return
     */
    @PostMapping
    public ResponseEntity createStudent(@RequestBody @Valid StudentDto studentDto, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        studentValidator.validate(studentDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        return studentService.createStudent(studentDto);
    }

    /**
     * 학생 한명 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity getStudent(@PathVariable Integer id) {
        return studentService.getStudent(id);
    }

    /**
     * 학생들의 정보 조회(페이징)
     *
     * @param pageable
     * @param assembler
     * @return
     */
    @GetMapping
    public ResponseEntity queryStudents(Pageable pageable, PagedResourcesAssembler<Student> assembler) {
        return studentService.queryStudent(pageable, assembler);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStudent(@PathVariable Integer id,
                                        @RequestBody @Valid StudentDto studentDto, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        return studentService.updateStudent(id, studentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable Integer id){
        return studentService.deleteStudent(id);
    }


    private ResponseEntity<?> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
}
