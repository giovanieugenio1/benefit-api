package com.corporate.benefits.benefit_api.mapper;

import com.corporate.benefits.benefit_api.dto.EmployeeDTO;
import com.corporate.benefits.benefit_api.entities.Employee;

public class EmployeeMapper {

    public static Employee toEmployee(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(employee.getId());
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setCpf(dto.getCpf());
        employee.setDepartment(dto.getDepartment());

        return employee;
    }

    public static EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setCpf(employee.getCpf());
        dto.setDepartment(employee.getDepartment());

        return dto;
    }
}
