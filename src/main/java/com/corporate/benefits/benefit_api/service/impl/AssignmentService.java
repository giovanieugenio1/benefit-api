package com.corporate.benefits.benefit_api.service.impl;

import com.corporate.benefits.benefit_api.dto.AssignmentDTO;
import com.corporate.benefits.benefit_api.entities.Benefit;
import com.corporate.benefits.benefit_api.entities.Employee;
import com.corporate.benefits.benefit_api.exceptions.ResourceNotFoundException;
import com.corporate.benefits.benefit_api.repository.BenefitRepository;
import com.corporate.benefits.benefit_api.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final EmployeeRepository employeeRepository;
    private final BenefitRepository benefitRepository;

    public void assignBenefitToEmployee(AssignmentDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(
                ()-> new ResourceNotFoundException("Employee not found with the provided id")
        );

        Benefit benefit = benefitRepository.findById(dto.getBenefitId()).orElseThrow(
                ()-> new ResourceNotFoundException("Benefit not found with the provided id")
        );

        benefit.setEmployee(employee);

        benefitRepository.save(benefit);
    }
}
