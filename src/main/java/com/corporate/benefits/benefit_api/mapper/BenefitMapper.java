package com.corporate.benefits.benefit_api.mapper;

import com.corporate.benefits.benefit_api.dto.BenefitDTO;
import com.corporate.benefits.benefit_api.entities.Benefit;

public class BenefitMapper {

    public static Benefit toBenefit(BenefitDTO dto) {
        Benefit benefit = new Benefit();
        benefit.setId(dto.getId());
        benefit.setName(dto.getName());
        benefit.setDescription(dto.getDescription());

        return benefit;
    }

    public static BenefitDTO toDTO(Benefit benefit) {
        BenefitDTO dto = new BenefitDTO();
        dto.setId(benefit.getId());
        dto.setName(benefit.getName());
        dto.setDescription(benefit.getDescription());

        return dto;
    }
}
