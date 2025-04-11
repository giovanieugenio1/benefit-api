package com.corporate.benefits.benefit_api.service;

import com.corporate.benefits.benefit_api.dto.EmployeeDTO;
import com.corporate.benefits.benefit_api.entities.Employee;
import com.corporate.benefits.benefit_api.exceptions.CpfValidationException;
import com.corporate.benefits.benefit_api.exceptions.EmailValidationException;
import com.corporate.benefits.benefit_api.repository.EmployeeRepository;
import com.corporate.benefits.benefit_api.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository repository;

    private EmployeeDTO dto;
    private Employee employee;

    @BeforeEach
    void setup() {
        dto = new EmployeeDTO();
        dto.setName("Giovani");
        dto.setCpf("34265478675");
        dto.setDepartment("TI");
        dto.setEmail("giovani@corp.com");

        employee = new Employee();
        employee.setId(1L);
        employee.setName("Giovani");
        employee.setCpf("34265478675");
        employee.setDepartment("TI");
        employee.setEmail("giovani@corp.com");
    }

    @Test
    void shouldCreateEmployee() {
        when(repository.findByCpf(dto.getCpf())).thenReturn(Optional.empty());
        when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(repository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO result = employeeService.create(dto);

        assertNotNull(result);
        assertEquals(dto.getEmail(), result.getEmail());
    }

    @Test
    void shouldThrowCpfValidationException() {
        when(repository.findByCpf(dto.getCpf())).thenReturn(Optional.of(employee));

        assertThrows(CpfValidationException.class, ()-> employeeService.create(dto));
    }

    @Test
    void shouldThrowEmailValidationException() {
        when(repository.findByCpf(dto.getCpf())).thenReturn(Optional.empty());
        when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.of(employee));

        assertThrows(EmailValidationException.class, () -> employeeService.create(dto));
    }

    @Test
    void shouldUpdateEmployee() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        when(repository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Fulano de tal");
        employeeDTO.setCpf("65487672314");
        employeeDTO.setDepartment("ADM");

        EmployeeDTO result = employeeService.update(1L, employeeDTO);

        assertNotNull(result);
        assertEquals("Fulano de tal", result.getName());
    }

    @Test
    void shouldDeleteEmployee() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void shouldFindAllEmployee() {
        when(repository.findAll()).thenReturn(List.of(employee));

        List<EmployeeDTO> result = employeeService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
