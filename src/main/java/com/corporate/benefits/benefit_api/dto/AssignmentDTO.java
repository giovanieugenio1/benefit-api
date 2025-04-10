package com.corporate.benefits.benefit_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AssignmentDTO {

    private Long id;
    private Long employeeId;
    private Long benefitId;
    private String assignedDate;
}