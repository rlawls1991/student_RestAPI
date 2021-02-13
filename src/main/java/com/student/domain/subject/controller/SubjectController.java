package com.student.domain.subject.controller;

import com.student.domain.StudentValidator;
import com.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
public class SubjectController {

    private final StudentService studentService;

    }
