package com.corporate.benefits.benefit_api.service;

import com.corporate.benefits.benefit_api.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO create(EmployeeDTO dto);
    EmployeeDTO update(Long id, EmployeeDTO dto);
    void delete(Long id);
    EmployeeDTO findById(Long id);
    List<EmployeeDTO> findAll();
}
