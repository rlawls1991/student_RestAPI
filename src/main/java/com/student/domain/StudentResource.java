package com.student.domain;

import com.student.domain.controller.StudentController;
import com.student.domain.dto.StudentDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class StudentResource extends EntityModel<StudentDto> {

    public StudentResource(StudentDto student, Link... links) {
        super(student, links);
        add(linkTo(StudentController.class).slash(student.getId()).withSelfRel());
    }
}
