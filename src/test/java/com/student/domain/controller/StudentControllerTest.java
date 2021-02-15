package com.student.domain.controller;

import com.student.config.BaseControllerTest;
import com.student.domain.dto.StudentDto;
import com.student.service.StudentService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

public class StudentControllerTest extends BaseControllerTest {

    @Autowired
    private StudentService studentService;

    @Test
    @DisplayName("정상적으로 학생을 생성하는 테스트")
    public void createStudent() throws Exception {
        StudentDto studentDto = StudentDto.builder()
                .name("테스트용")
                .address("경기도 안양시 안양2동")
                .age(31)
                .email("test@.test.com")
                .phone("010-1234-1234")
                .build();

        mockMvc.perform(post("/api/student")
                    .header(HttpHeaders.AUTHORIZATION)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON)
                    .content(this.objectMapper.writeValueAsString(studentDto)))
                    .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-student").exists())
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
                                fieldWithPath("name").description("Name of new studnet")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("id").description("Identifier of new student"),
                                fieldWithPath("name").description("Name of new student"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.query-students.href").description("link to query student list"),
                                fieldWithPath("_links.update-student.href").description("link to update existing student"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))
        ;
    }
}