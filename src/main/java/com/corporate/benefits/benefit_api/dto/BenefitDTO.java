package com.corporate.benefits.benefit_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BenefitDTO {

    private Long id;

    @NotBlank(message = "Benefit name is required")
    @Size(max = 100, message = "The name can have a maximum of 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 170, message = "The name can have a maximum of 170 characters")
    private String description;

    private boolean active = true;
}
