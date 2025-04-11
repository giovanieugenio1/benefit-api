package com.corporate.benefits.benefit_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AssignmentDTO {

    private Long id;

    @JsonProperty("employeeId")
    @NotNull(message = "Employee ID must not be null")
    private Long employeeId;

    @JsonProperty("benefitId")
    @NotNull(message = "Benefit ID must not be null")
    private Long benefitId;

    @JsonProperty("assignedDate")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "The date must be in the format yyyy-MM-dd")
    private String assignedDate;
}