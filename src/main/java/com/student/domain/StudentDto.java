package com.student.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class StudentDto {

    @NotEmpty
    private String name;
    @Min(1)
    private int age;
    @NotBlank
    private String phone;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String address;
    @NotNull
    private LocalDateTime createDateTime;
}
