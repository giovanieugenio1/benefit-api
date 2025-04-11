package com.corporate.benefits.benefit_api.service.impl;

import com.corporate.benefits.benefit_api.dto.EmployeeDTO;
import com.corporate.benefits.benefit_api.entities.Employee;
import com.corporate.benefits.benefit_api.exceptions.CpfValidationException;
import com.corporate.benefits.benefit_api.exceptions.EmailValidationException;
import com.corporate.benefits.benefit_api.exceptions.ResourceNotFoundException;
import com.corporate.benefits.benefit_api.mapper.EmployeeMapper;
import com.corporate.benefits.benefit_api.repository.EmployeeRepository;
import com.corporate.benefits.benefit_api.service.EmployeeService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.corporate.benefits.benefit_api.mapper.EmployeeMapper.toDTO;
import static com.corporate.benefits.benefit_api.mapper.EmployeeMapper.toEmployee;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO create(EmployeeDTO dto) {
        cpfValidation(dto);
        emailValidation(dto);
        Employee employee = toEmployee(dto);
        return toDTO(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO dto) {
        Employee employee = getEmployeeById(id);
        employee.setName(dto.getName());
        employee.setCpf(dto.getCpf());
        employee.setDepartment(dto.getDepartment());

        return toDTO(employeeRepository.save(employee));
    }

    @Override
    public void delete(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.deleteById(employee.getId());
    }

    @Override
    public EmployeeDTO findById(Long id) {
        Employee employee = getEmployeeById(id);

        return toDTO(employee);
    }

    @Override
    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee not found with the provided id")
        );
        return employee;
    }

    private void cpfValidation(EmployeeDTO dto) {
        boolean exists = employeeRepository.findByCpf(dto.getCpf()).isPresent();
        if (exists) {
            throw new CpfValidationException("CPF already exists, try to login");
        }
    }

    private void emailValidation(EmployeeDTO dto) {
        boolean exists = employeeRepository.findByEmail(dto.getEmail()).isPresent();
        if (exists) {
            throw new EmailValidationException("E-mail already exists, try to login");
        }
    }
}
