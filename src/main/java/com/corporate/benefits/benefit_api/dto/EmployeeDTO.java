package com.corporate.benefits.benefit_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Benefit name is required")
    @Size(max = 100, message = "The name can have a maximum of 100 characters")
    private String name;

    @Email(message = "the email must be in a valid format")
    private String email;

    @NotBlank(message = "CPF is required")
    @Size(min = 11, max = 11, message = "CPF must contain exactly 11 digits")
    private String cpf;

    @NotBlank(message = "Department is required")
    private String department;
}
