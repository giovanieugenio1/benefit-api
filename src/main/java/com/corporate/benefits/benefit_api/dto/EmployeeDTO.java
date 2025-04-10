package com.corporate.benefits.benefit_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeDTO {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String department;
}
