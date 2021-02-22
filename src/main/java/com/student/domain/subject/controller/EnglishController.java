package com.student.domain.subject.controller;

import com.student.Error.ErrorsResource;
import com.student.domain.Student;
import com.student.domain.StudentResource;
import com.student.domain.controller.StudentController;
import com.student.domain.dto.StudentDto;
import com.student.domain.service.StudentService;
import com.student.domain.subject.SubjectKindStatus;
import com.student.domain.subject.english.EnglishStatus;
import com.student.domain.subject.english.EnglishValidator;
import com.student.domain.subject.dto.EnglishInputDto;
import com.student.domain.subject.service.EnglishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.EnglishEnums;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@Controller
@RequestMapping(value = "/api", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EnglishController {

    private final EnglishValidator englishValidator;
    private final EnglishService englishService;
    private final StudentService studentService;

    @PostMapping(value = "/{id}/english")
    public ResponseEntity createSubject(@PathVariable Long id, @Valid @RequestBody EnglishInputDto englishInputDto, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        englishValidator.validate(englishInputDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        StudentDto checkStudent = studentService.getStudent(id);
        if (checkStudent == null) {
            return ResponseEntity.notFound().build();
        }

        StudentDto student = englishService.createEnglish(id, englishInputDto);

        var selfLinkBuilder = linkTo(EnglishController.class).slash(student.getId());
        URI createdUri = selfLinkBuilder.toUri();
        StudentResource studentResource = new StudentResource(student);
        studentResource.add(linkTo(StudentController.class).withRel("query-english"));
        studentResource.add(selfLinkBuilder.withRel("update-english"));
        studentResource.add(Link.of("/docs/index.html#resources-english-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(studentResource);
    }

    @DeleteMapping(value = "/{id}/english")
    public ResponseEntity deleteSubject(@PathVariable Long id, @NotEmpty @RequestBody SubjectKindStatus subjectKindStatus, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        StudentDto checkStudent = studentService.getStudent(id);
        if (checkStudent == null) {
            return ResponseEntity.notFound().build();
        }

        StudentDto student = englishService.removeEnglish(id, subjectKindStatus);

        var selfLinkBuilder = linkTo(EnglishController.class).slash(student.getId());
        URI createdUri = selfLinkBuilder.toUri();
        StudentResource studentResource = new StudentResource(student);
        studentResource.add(Link.of("/docs/index.html#resources-english-delete").withRel("profile"));

        return ResponseEntity.ok(studentResource);
    }



    private ResponseEntity<?> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
}
