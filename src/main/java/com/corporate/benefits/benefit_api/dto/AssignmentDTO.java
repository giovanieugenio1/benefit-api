package com.corporate.benefits.benefit_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AssignmentDTO {

    private Long id;

    @NotNull(message = "Employee ID must not be null")
    private Long employeeId;

    @NotNull(message = "Benefit ID must not be null")
    private Long benefitId;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "The date must be in the format yyyy-MM-dd")
    private String assignedDate;
}