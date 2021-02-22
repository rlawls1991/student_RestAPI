package com.student.domain.subject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.domain.Student;
import com.student.domain.common.RestDocsConfiguration;
import com.student.domain.repository.StudentRepository;
import com.student.domain.subject.SubjectKindStatus;
import com.student.domain.subject.dto.EnglishInputDto;
import com.student.domain.subject.english.EnglishStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
@Transactional(readOnly = true)
public class EnglishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    StudentRepository studentRepository;


    @Test
    @Transactional
    @DisplayName("정상적으로 영어점수를 생성하는 테스트")
    public void createEnglish() throws Exception {
        Student student = saveStudent(100);
        EnglishInputDto englishInputDto = EnglishInputDto.builder()
                .grade("A")
                .score(100)
                .classCode("ABC")
                .professorName("테스트용")
                .subjectKindStatus(SubjectKindStatus.FINAL)
                .englishStatus(EnglishStatus.GRAMMAR)
                .build();

        ResultActions perform = mockMvc.perform(post("/api/{id}/english", student.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(englishInputDto)))
                .andDo(print());

        perform.andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("grades").exists())
                .andDo(document("create-english",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-english").description("link to query english"),
                                linkWithRel("update-english").description("link to update an existing subject"),
                                linkWithRel("profile").description("link to profile")

                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        relaxedRequestFields(
                                fieldWithPath("score").description("score of new english"),
                                fieldWithPath("classCode").description("classCode of new english"),
                                fieldWithPath("professorName").description("professorName of new english"),
                                fieldWithPath("subjectKindStatus").description("subjectKindStatus of new english"),
                                fieldWithPath("englishStatus").description("englishStatus of new english")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("id").description("id of new english"),
                                fieldWithPath("grades").description("grade of new english"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.update-english.href").description("link to update existing english"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))
        ;
    }

    @Test
    @Transactional
    @DisplayName("입력받을 수 없는 값을 사용한 경우에 에러가 발생")
    public void createEnglish_Bad_Request() throws Exception {
        Student student = saveStudent(100);
        Student studentParam = Student.builder()
                .name("테스트용")
                .address("경기도 안양시")
                .age(31)
                .email("test@test.com")
                .phone("010-1234-1234")
                .build();

        ResultActions perform = mockMvc.perform(post("/api/{id}/english", student.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(studentParam)))
                .andDo(print());

        perform.andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @Transactional
    @DisplayName("입력값이 잘못들어갔을때 에러가 발생")
    public void createEnglish_Bad_Request_Wrong_Input() throws Exception {
        Student student = saveStudent(100);
        EnglishInputDto englishInputDto = EnglishInputDto.builder()
                .grade("")
                .score(300)
                .classCode("")
                .professorName("")
                .subjectKindStatus(SubjectKindStatus.FINAL)
                .englishStatus(EnglishStatus.GRAMMAR)
                .build();

        ResultActions perform = mockMvc.perform(post("/api/{id}/english", student.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(englishInputDto)))
                .andDo(print());

        perform.andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @Transactional
    @DisplayName("입력값이 비어있는 경우에 에러가 발생")
    public void createEnglish_Bad_Request_Empty_Input() throws Exception {
        Student student = saveStudent(100);
        EnglishInputDto englishInputDto = EnglishInputDto.builder().build();

        ResultActions perform = mockMvc.perform(post("/api/{id}/english", student.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(englishInputDto)))
                .andDo(print());

        perform.andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }


    @Test
    @Transactional
    @DisplayName("정상적으로 점수 과목 삭제")
    public void deleteEnglish() throws Exception {
        Student student = saveStudent(100);
        SubjectKindStatus subjectKindStatus = SubjectKindStatus.MIDTERM;

        ResultActions perform = mockMvc.perform(delete("/api/{id}/english", student.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(subjectKindStatus)))
                .andDo(print());

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
        ;
    }

    @Test
    @Transactional
    @DisplayName("입력받을 수 없는 파라미터를 받은 경우에 에러 발생")
    public void deleteEnglish_Bad_Request() throws Exception {
        Student student = saveStudent(100);

        ResultActions perform = mockMvc.perform(delete("/api/{id}/english", student.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(student)))
                .andDo(print());

        perform.andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("입력값이 비어있을때 에러가 발생")
    public void deleteEnglish_Bad_Request_Empty_Value() throws Exception {
        Student student = saveStudent(100);

        ResultActions perform = mockMvc.perform(delete("/api/{id}/english", student.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print());

        perform.andExpect(status().isBadRequest());
    }

    private Student saveStudent(int index) {
        Student student = Student.builder()
                .name("test" + index)
                .address("경기도 안양시")
                .age(31)
                .email("test@test.com")
                .phone("010-1234-1234")
                .build();
        student.createSetting();

        return studentRepository.save(student);
    }
}