package com.student.domain.subject.service;

import com.student.domain.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl {

    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;

}
