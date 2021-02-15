package com.student.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.domain.Student;
import com.student.domain.common.RestDocsConfiguration;
import com.student.domain.dto.StudentDto;
import com.student.domain.repository.StudentRepositoryCustom;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
@Transactional(readOnly = true)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    StudentRepositoryCustom studentRepository;


    @Test
    @Transactional
    @DisplayName("정상적으로 학생을 생성하는 테스트")
    public void createStudent() throws Exception {
        StudentDto studentDto = StudentDto.builder()
                .name("테스트용")
                .address("경기도 안양시")
                .age(31)
                .email("test@test.com")
                .phone("010-1234-1234")
                .build();

        ResultActions perform = mockMvc.perform(post("/api/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(studentDto)))
                .andDo(print());

        perform.andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("address").exists())
                .andExpect(jsonPath("age").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("phone").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-students").exists())
                .andExpect(jsonPath("_links.update-student").exists())
                .andDo(document("create-student",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-students").description("link to query students"),
                                linkWithRel("update-student").description("link to update an existing student"),
                                linkWithRel("profile").description("link to profile")

                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        relaxedRequestFields(
                                fieldWithPath("name").description("Name of new student"),
                                fieldWithPath("address").description("address of new student"),
                                fieldWithPath("age").description("age of new student"),
                                fieldWithPath("email").description("email of new student"),
                                fieldWithPath("phone").description("phone of new student")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("id").description("Identifier of new student"),
                                fieldWithPath("address").description("address of new student"),
                                fieldWithPath("age").description("age of new student"),
                                fieldWithPath("email").description("email of new student"),
                                fieldWithPath("phone").description("phone of new student"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.query-students.href").description("link to query student list"),
                                fieldWithPath("_links.update-student.href").description("link to update existing student"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))
        ;
    }

    @Test
    @Transactional
    @DisplayName("입력 받을 수 없는 값을 사용한 경우에 에러가 발생한하는 테스트")
    public void createStudent_Bad_Request() throws Exception {
        Student student = Student.builder()
                .name("테스트용")
                .address("경기도 안양시")
                .age(31)
                .email("test@test.com")
                .phone("010-1234-1234")
                .build();

        ResultActions perform = mockMvc.perform(post("/api/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(student)))
                .andDo(print());

        perform.andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("입력값이 비어있는 경우에 에러가 발생하는 테스트")
    public void createStudent_Bad_Request_Empty_Input() throws Exception {
        StudentDto studentDto = StudentDto.builder().build();

        this.mockMvc.perform(post("/api/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("입력값이 잘못들어가 에러가 발생하는 테스트")
    public void createStudent_Bad_Request_Wrong_Input() throws Exception {
        StudentDto studentDto = StudentDto.builder()
                .name("12345678910")
                .address("경기도 안양시")
                .age(31)
                .email("test@test.com")
                .phone("010-1234-1234")
                .build();

        this.mockMvc.perform(post("/api/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("30개의 이벤트를 10개식 두번째 페이지 조회하기")
    public void queryStudents() throws Exception {
        // When
        ResultActions perform = mockMvc.perform(get("/api/student")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "name,DESC")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON))
                .andDo(print());

        // Then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.studentList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-events"))
        ;
    }

}