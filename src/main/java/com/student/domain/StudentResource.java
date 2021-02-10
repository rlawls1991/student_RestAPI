package com.student.domain;

import com.student.domain.controller.StudentController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class StudentResource extends EntityModel<Student> {

    public StudentResource(Student student, Link... links) {
        super(student, links);
        add(linkTo(StudentController.class).slash(student.getId()).withSelfRel());
    }
}
