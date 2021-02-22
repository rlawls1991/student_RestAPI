package com.student.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.domain.Student;
import com.student.domain.common.RestDocsConfiguration;
import com.student.domain.dto.StudentDto;
import com.student.domain.dto.StudentInputDto;
import com.student.domain.repository.StudentRepository;
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

import javax.persistence.EntityManager;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    StudentRepository studentRepository;

    @Test
    @Transactional
    @DisplayName("정상적으로 학생을 생성하는 테스트")
    public void createStudent() throws Exception {
        StudentInputDto studentInputDto = StudentInputDto.builder()
                .name("테스트용")
                .address("경기도 안양시")
                .age(31)
                .email("test@test.com")
                .phone("010-1234-1234")
                .build();

        ResultActions perform = mockMvc.perform(post("/api/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(studentInputDto)))
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
    @DisplayName("30명의 학생들을 10개식 조회하기")
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
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-students"))
        ;
    }

    @Test
    @Transactional
    @DisplayName("기존의 학생를 하나 조회하기")
    public void getStudent() throws Exception {
        // Given
        Student student = saveStudent(100);

        // When & Then
        this.mockMvc.perform(get("/api/student/{id}", student.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("get-an-student"))
        ;
    }

    @Test
    @DisplayName("없는 학생을 조회했을 때 404 응답")
    public void getStudent404() throws Exception {
        // When & Then
        this.mockMvc.perform(get("/api/student/{id}", 1004))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @DisplayName("데이터 수정")
    public void updateStudent() throws Exception {
        // Given
        Student student = saveStudent(1001);

        StudentInputDto studentInputDto = StudentInputDto.builder()
                .name("수정" + student.getId())
                .email("update@test.com")
                .address("수정된주소")
                .phone("010-4321-1234")
                .age(30)
                .build();

        this.mockMvc.perform(put("/api/student/{id}", String.valueOf(student.getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(studentInputDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(studentInputDto.getName()))
                .andExpect(jsonPath("email").value(studentInputDto.getEmail()))
                .andExpect(jsonPath("address").value(studentInputDto.getAddress()))
                .andExpect(jsonPath("phone").value(studentInputDto.getPhone()))
                .andExpect(jsonPath("age").value(studentInputDto.getAge()))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("update-student"))
        ;
    }

    @Test
    @Transactional
    @DisplayName("입력 받을 수 없는 값을 사용한 경우에 에러가 발생한하는 테스트")
    public void updateStudent_Bad_Request() throws Exception {
        // Given
        Student student = saveStudent(1001);

        this.mockMvc.perform(put("/api/student/{id}", String.valueOf(student.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @Transactional
    @DisplayName("없는 학생을 수정했을 때 404응답")
    public void updateStudent404() throws Exception {
        // Given
        StudentInputDto studentInputDto = StudentInputDto.builder()
                .name("테스트용")
                .address("경기도 안양시")
                .age(31)
                .email("test@test.com")
                .phone("010-1234-1234")
                .build();

        // When & Then
        this.mockMvc.perform(put("/api/student/{id}", 1004)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentInputDto)))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @Transactional
    @DisplayName("기존의 이벤트를 하나 삭제하기")
    public void deleteEvent() throws Exception {
        // Given
        Student student = saveStudent(1001);

        this.mockMvc.perform(delete("/api/student/{id}", String.valueOf(student.getId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaTypes.HAL_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.profile").exists())
        ;
    }

    @Test
    @DisplayName("없는 학생을 삭제했을 때 404 응답")
    public void deleteStudent404() throws Exception {
        // When & Then
        this.mockMvc.perform(delete("/api/student/{id}", 1004))
                .andExpect(status().isNotFound());
    }

    private Student saveStudent(int index){
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