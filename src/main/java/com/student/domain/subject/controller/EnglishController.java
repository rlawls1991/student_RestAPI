package com.student.domain.subject.controller;

import com.student.Error.ErrorsResource;
import com.student.domain.subject.english.EnglishValidator;
import com.student.domain.subject.dto.EnglishInputDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(value = "/api/{id}/english", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EnglishController {

    private final EnglishValidator englishValidator;

    @PostMapping
    public ResponseEntity createResponse(@PathVariable Long id, @Valid @RequestBody EnglishInputDto englishInputDto, Errors errors) {

        if (errors.hasErrors()) {
            return badRequest(errors);
        }
        englishValidator.validate(englishInputDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        return  ResponseEntity.ok(null);
    }

    private ResponseEntity<?> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
}
