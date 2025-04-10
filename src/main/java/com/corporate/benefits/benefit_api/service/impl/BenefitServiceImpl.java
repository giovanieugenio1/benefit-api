package com.corporate.benefits.benefit_api.service.impl;

import com.corporate.benefits.benefit_api.dto.BenefitDTO;
import com.corporate.benefits.benefit_api.entities.Benefit;
import com.corporate.benefits.benefit_api.exceptions.BenefitAlreadyExistsException;
import com.corporate.benefits.benefit_api.exceptions.ResourceNotFoundException;
import com.corporate.benefits.benefit_api.mapper.BenefitMapper;
import com.corporate.benefits.benefit_api.repository.BenefitRepository;
import com.corporate.benefits.benefit_api.service.BenefitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.corporate.benefits.benefit_api.mapper.BenefitMapper.toBenefit;
import static com.corporate.benefits.benefit_api.mapper.BenefitMapper.toDTO;

@Service
@RequiredArgsConstructor
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;

    @Override
    public BenefitDTO create(BenefitDTO dto) {
        benefitValidationName(dto);
        Benefit benefit = toBenefit(dto);
        return toDTO(benefitRepository.save(benefit));
    }

    @Override
    public BenefitDTO update(Long id, BenefitDTO dto) {
        Benefit benefit = getBenefitById(id);
        benefit.setName(dto.getName());
        benefit.setDescription(dto.getDescription());
        benefit.setActive(dto.isActive());

        return toDTO(benefitRepository.save(benefit));
    }

    @Override
    public void delete(Long id) {
        findById(id);
        benefitRepository.deleteById(id);
    }

    @Override
    public BenefitDTO findById(Long id) {
        Benefit benefit = benefitRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Benefit not found with the provided id")
        );
        return toDTO(benefit);
    }

    @Override
    public List<BenefitDTO> findAll() {
        return benefitRepository.findAll()
                .stream()
                .map(BenefitMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void benefitValidationName(BenefitDTO dto) {
        boolean exists = benefitRepository.findByName(dto.getName()).isPresent();
        if (exists) {
            throw new BenefitAlreadyExistsException("There is already a benefit registered with that name");
        }
    }

    private Benefit getBenefitById(Long id) {
        return benefitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Benefit not found with the provided id"));
    }
}
