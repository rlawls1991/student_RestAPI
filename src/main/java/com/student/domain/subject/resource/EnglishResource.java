package com.student.domain.subject.resource;

import com.student.domain.controller.StudentController;
import com.student.domain.subject.dto.EnglishDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EnglishResource extends EntityModel<EnglishDto> {

    public EnglishResource(EnglishDto englishDto, Link... links) {
        super(englishDto, links);
        add(linkTo(StudentController.class).slash(englishDto.getId()).withSelfRel());
    }
}
