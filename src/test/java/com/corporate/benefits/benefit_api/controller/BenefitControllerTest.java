package com.corporate.benefits.benefit_api.controller;

import com.corporate.benefits.benefit_api.config.TestSecurityConfig;
import com.corporate.benefits.benefit_api.dto.BenefitDTO;
import com.corporate.benefits.benefit_api.exceptions.ResourceNotFoundException;
import com.corporate.benefits.benefit_api.security.JwtAuthFilter;
import com.corporate.benefits.benefit_api.service.BenefitService;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = BenefitController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthFilter.class
        )
)
@Import(TestSecurityConfig.class)
class BenefitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BenefitService benefitService;

    @Autowired
    private ObjectMapper objectMapper;

    private BenefitDTO createBenefitDTO(Long id, String name, String description, boolean active) {
        BenefitDTO dto = new BenefitDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        dto.setActive(active);

        return dto;
    }

    @Test
    @DisplayName("POST /benefit - Should return 201 when benefit is created")
    void shouldCreateBenefit() throws Exception {
        BenefitDTO request = createBenefitDTO(null, "Vale Alimentação", "Descrição VA", true);
        BenefitDTO response = createBenefitDTO(1L, "Vale Alimentação", "Descrição VA", true);

        when(benefitService.create(any(BenefitDTO.class))).thenReturn(response);

        mockMvc.perform(post("/benefit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Vale Alimentação"));
    }

    @Test
    @DisplayName("POST /benefit - Should return 400 when request is invalid")
    void shouldReturnBadRequestWhenCreateWithInvalidData() throws Exception {
        BenefitDTO invalidRequest = createBenefitDTO(null, "", "", true);

        mockMvc.perform(post("/benefit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.name").value("Benefit name is required"));
    }

    @Test
    @DisplayName("PUT /benefit/{id} - Should return 200 when benefit is updated")
    void shouldUpdateBenefit() throws Exception {
        Long id = 1L;

        BenefitDTO request = createBenefitDTO(id, "Vale Alimentação Atualizado", "Nova descrição", false);
        BenefitDTO response = createBenefitDTO(id, "Vale Alimentação Atualizado", "Nova descrição", false);

        when(benefitService.update(eq(id), any(BenefitDTO.class))).thenReturn(response);

        mockMvc.perform(put("/benefit/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Vale Alimentação Atualizado"));
    }

    @Test
    @DisplayName("DELETE /benefit/{id} - Should return 204 when benefit is deleted")
    void shouldDeleteBenefit() throws Exception {
        Long id = 1L;

        doNothing().when(benefitService).delete(id);

        mockMvc.perform(delete("/benefit/{id}", id))
                .andExpect(status().isNoContent());

        verify(benefitService).delete(id);
    }

    @Test
    @DisplayName("GET /benefit/{id} - Should return 200 when benefit exists")
    void shouldReturnBenefitWhenExists() throws Exception {
        Long id = 1L;

        BenefitDTO response = createBenefitDTO(id, "Vale Transporte", "Descrição VT", true);

        when(benefitService.findById(id)).thenReturn(response);

        mockMvc.perform(get("/benefit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Vale Transporte"));
    }

    @Test
    @DisplayName("GET /benefit/{id} - Should return 404 when benefit not found")
    void shouldReturnNotFoundWhenBenefitNotExists() throws Exception {
        Long id = 1L;

        when(benefitService.findById(id)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/benefit/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /benefit - Should return all benefits")
    void shouldReturnAllBenefits() throws Exception {
        List<BenefitDTO> benefits = Arrays.asList(
                createBenefitDTO(1L, "Vale Alimentação", "Descrição VA", true),
                createBenefitDTO(2L, "Vale Transporte", "Descrição VT", true)
        );

        when(benefitService.findAll()).thenReturn(benefits);

        mockMvc.perform(get("/benefit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Vale Alimentação"))
                .andExpect(jsonPath("$[1].name").value("Vale Transporte"));
    }
}
