package com.corporate.benefits.benefit_api.service;

import com.corporate.benefits.benefit_api.dto.BenefitDTO;

import java.util.List;

public interface BenefitService {

    BenefitDTO create(BenefitDTO dto);

    BenefitDTO update(Long id, BenefitDTO dto);

    void delete(Long id);

    BenefitDTO findById(Long id);

    List<BenefitDTO> findAll();
}
