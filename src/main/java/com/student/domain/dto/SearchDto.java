package com.student.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private String name;
    private int age;
    private String phone;
    private String email;
    private String address;
    private int id;
}
