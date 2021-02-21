package com.student.domain.subject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.domain.common.RestDocsConfiguration;
import com.student.domain.subject.SubjectKindStatus;
import com.student.domain.subject.english.EnglishStatus;
import com.student.domain.subject.dto.EnglishInputDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
@Transactional(readOnly = true)
public class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createEnglish() throws Exception {
        EnglishInputDto englishInputDto = EnglishInputDto.builder()
                .grade("A")
                .score(100)
                .subjectKindStatus(SubjectKindStatus.FINAL)
                .englishStatus(EnglishStatus.GRAMMAR)
                .build();

        mockMvc.perform(post("/api/{id}/english", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(this.objectMapper.writeValueAsString(englishInputDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}