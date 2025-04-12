package com.corporate.benefits.benefit_api.controller;

import com.corporate.benefits.benefit_api.config.TestSecurityConfig;
import com.corporate.benefits.benefit_api.dto.EmployeeDTO;
import com.corporate.benefits.benefit_api.exceptions.ResourceNotFoundException;
import com.corporate.benefits.benefit_api.security.JwtAuthFilter;
import com.corporate.benefits.benefit_api.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = EmployeeController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthFilter.class
        )
)
@Import(TestSecurityConfig.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("POST /employee - Should return 201 Created when employee is created")
    void shouldCreateEmployee() throws Exception {
        EmployeeDTO request = new EmployeeDTO(null, "Giovani", "giovani@corp.com", "12345678900", "TI");
        EmployeeDTO response = new EmployeeDTO(1L, "Giovani", "giovani@corp.com", "12345678900", "TI");

        when(employeeService.create(any(EmployeeDTO.class))).thenReturn(response);

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Giovani"));
    }

    @Test
    @DisplayName("POST /employee - Should return 400 when request is invalid")
    void shouldReturnBadRequestWhenCreateWithInvalidData() throws Exception {
        EmployeeDTO invalid = new EmployeeDTO(null, "", "", "2324", "");

        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /employee/{id} - Should return 200 when employee is updated")
    void shouldUpdateEmployee() throws Exception {
        Long id = 1L;

        EmployeeDTO request = new EmployeeDTO(id, "Giovani Eugenio", "giovani.updated@corp.com", "33245675456", "Financial");
        EmployeeDTO response = new EmployeeDTO(id, "Giovani Eugenio", "giovani.updated@corp.com", "33245675456", "Financial");

        when(employeeService.update(eq(id), any(EmployeeDTO.class))).thenReturn(response);

        mockMvc.perform(put("/employee/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Giovani Eugenio"));
    }

    @Test
    @DisplayName("PUT /employee/{id} - Should return 404 when employee not found")
    void shouldReturnNotFoundWhenUpdatingNonExistingEmployee() throws Exception {
        Long nonExistentId = 99L;

        EmployeeDTO request = new EmployeeDTO(nonExistentId, "Giovani", "giovani@corp.com", "12345678900", "TI");

        when(employeeService.update(eq(nonExistentId), any(EmployeeDTO.class)))
                .thenThrow(new ResourceNotFoundException("Employee not found with the provided id"));

        mockMvc.perform(put("/employee/{id}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /employee/{id} - Should return 204 when employee is deleted")
    void shouldDeleteEmployee() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/employee/{id}", id))
                .andExpect(status().isNoContent());

        verify(employeeService).delete(id);
    }

    @Test
    @DisplayName("DELETE /employee/{id} - Should return 404 when employee not found")
    void shouldReturnNotFoundWhenDeletingNonExistingEmployee() throws Exception {
        Long nonExistentId = 99L;

        doThrow(new ResourceNotFoundException("Employee not found with the provided id"))
                .when(employeeService).delete(nonExistentId);

        mockMvc.perform(delete("/employee/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /employee/{id} - Should return 200 with employee when found")
    void shouldReturnEmployeeWhenFound() throws Exception {
        Long id = 1L;

        EmployeeDTO response = new EmployeeDTO(id, "Giovani", "giovani@corp.com", "12345678900", "TI");

        when(employeeService.findById(id)).thenReturn(response);

        mockMvc.perform(get("/employee/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Giovani"));
    }

    @Test
    @DisplayName("GET /employee/{id} - Should return 404 when employee not found")
    void shouldReturnNotFoundWhenEmployeeNotFound() throws Exception {
        Long nonExistingId = 99L;

        when(employeeService.findById(nonExistingId)).thenReturn(null);

        mockMvc.perform(get("/employee/{id}", nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /employee - Should return 200 with all employees")
    void shouldReturnAllEmployees() throws Exception {
        List<EmployeeDTO> employees = List.of(
                new EmployeeDTO(1L, "João Silva", "joao@corp.com", "12345678900", "TI"),
                new EmployeeDTO(2L, "Maria Souza", "maria@corp.com", "98765432100", "RH")
        );

        when(employeeService.findAll()).thenReturn(employees);

        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("João Silva"))
                .andExpect(jsonPath("$[1].name").value("Maria Souza"));
    }

    @Test
    @DisplayName("GET /employee - Should return 200 with empty list when no employees")
    void shouldReturnEmptyListWhenNoEmployees() throws Exception {
        when(employeeService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("POST /employee - Should return 400 with multiple validation errors")
    void shouldReturnBadRequestWithMultipleErrors() throws Exception {
        EmployeeDTO invalid = new EmployeeDTO(null, "", "email-invalido", "123", "");

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.name").exists())
                .andExpect(jsonPath("$.message.email").exists())
                .andExpect(jsonPath("$.message.cpf").exists())
                .andExpect(jsonPath("$.message.department").exists());
    }
}
