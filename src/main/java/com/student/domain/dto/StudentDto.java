package com.student.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    public StudentDto(@NotEmpty String name, @Min(1) int age, @NotBlank String phone, @NotBlank @Email String email, @NotBlank String address) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.createDateTime = LocalDateTime.now();
    }

    @NotEmpty
    private String name;
    @Min(1)
    private int age;
    @NotBlank
    private String phone;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String address;
    private LocalDateTime createDateTime;
}
