package com.corporate.benefits.benefit_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BenefitDTO {

    private Long id;
    private String name;
    private String description;
    private boolean active;
}
