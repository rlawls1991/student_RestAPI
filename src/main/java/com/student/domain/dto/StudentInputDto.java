package com.student.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentInputDto {
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
}
