package com.corporate.benefits.benefit_api.service;

import com.corporate.benefits.benefit_api.dto.BenefitDTO;
import com.corporate.benefits.benefit_api.entities.Benefit;
import com.corporate.benefits.benefit_api.exceptions.BenefitAlreadyExistsException;
import com.corporate.benefits.benefit_api.exceptions.ResourceNotFoundException;
import com.corporate.benefits.benefit_api.repository.BenefitRepository;
import com.corporate.benefits.benefit_api.service.impl.BenefitServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BenefitServiceImplTest {

    @Mock
    private BenefitRepository benefitRepository;

    @InjectMocks
    private BenefitServiceImpl benefitService;

    private Benefit createBenefit(Long id, String name, String description, boolean active) {
        Benefit benefit = new Benefit();
        benefit.setId(id);
        benefit.setName(name);
        benefit.setDescription(description);
        benefit.setActive(active);

        return benefit;
    }

    private BenefitDTO createBenefitDTO(Long id, String name, String description, boolean active) {
        BenefitDTO dto = new BenefitDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        dto.setActive(active);

        return dto;
    }

    @Test
    @DisplayName("Create benefit - Should return created benefit when valid data")
    void shouldCreateBenefitWhenValidData() {
        BenefitDTO request = createBenefitDTO(null, "Vale Alimentação", "Benefício de valor disponível para alimentação", true);
        Benefit savedBenefit = createBenefit(1L, "Vale Alimentação", "Benefício de valor disponível para alimentação", true);

        when(benefitRepository.findByName(request.getName())).thenReturn(Optional.empty());
        when(benefitRepository.save(any(Benefit.class))).thenReturn(savedBenefit);

        BenefitDTO result = benefitService.create(request);

        assertNotNull(result.getId());
        assertEquals("Vale Alimentação", result.getName());
        verify(benefitRepository).findByName(request.getName());
        verify(benefitRepository).save(any(Benefit.class));
    }

    @Test
    @DisplayName("Create benefit - Should throw exception when name already exists")
    void shouldThrowExceptionWhenNameExists() {
        BenefitDTO request = createBenefitDTO(null, "Seguro de vida", "descrição1", true);
        Benefit existing = createBenefit(1L, "Seguro de vida", "Nova descrição", true);

        when(benefitRepository.findByName(request.getName())).thenReturn(Optional.of(existing));

        assertThrows(BenefitAlreadyExistsException.class, ()-> benefitService.create(request));
        verify(benefitRepository).findByName(request.getName());
        verify(benefitRepository, never()).save(existing);
    }

    @Test
    @DisplayName("Update benefit - Should return updated benefit when valid data")
    void shouldUpdateBenefitWhenValidData() {
        Long id = 1L;

        Benefit existingBenefit = createBenefit(id, "Vale Transporte", "descrição1", true);
        BenefitDTO updateRequest = createBenefitDTO(id, "Vale Transporte Atualizado", "Nova descrição", false);
        Benefit updatedBenefit = createBenefit(id, "Vale Transporte Atualizado", "Nova descrição", false);

        when(benefitRepository.findById(id)).thenReturn(Optional.of(existingBenefit));
        when(benefitRepository.save(any(Benefit.class))).thenReturn(updatedBenefit);

        BenefitDTO result = benefitService.update(id, updateRequest);

        assertEquals(id, result.getId());
        assertEquals("Vale Transporte Atualizado", result.getName());
        assertEquals("Nova descrição", result.getDescription());
        assertFalse(result.isActive());
        verify(benefitRepository).findById(id);
        verify(benefitRepository).save(any(Benefit.class));
    }

    @Test
    @DisplayName("Update benefit - Should throw exception when benefit not found")
    void shouldThrowExceptionWhenUpdatingNonExistingBenefit() {
        Long nonExistingId = 1L;

        BenefitDTO updateRequest = createBenefitDTO(nonExistingId, "Nome", "Descrição", true);

        when(benefitRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> benefitService.update(nonExistingId, updateRequest));
        verify(benefitRepository).findById(nonExistingId);
        verify(benefitRepository, never()).save(any());
    }

    @Test
    @DisplayName("Delete benefit - Should delete when benefit exists")
    void shouldDeleteBenefitWhenExists() {
        Long benefitId = 1L;

        Benefit benefit = createBenefit(benefitId, "Vale", "Desc", true);

        when(benefitRepository.findById(benefitId)).thenReturn(Optional.of(benefit));

        benefitService.delete(benefitId);

        verify(benefitRepository).delete(benefit);
    }

    @Test
    @DisplayName("Delete benefit - Should throw exception when benefit not found")
    void shouldThrowExceptionWhenDeletingNonExistingBenefit() {
        Long nonExistingId = 99L;

        when(benefitRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> benefitService.delete(nonExistingId));
        verify(benefitRepository, never()).deleteById(any());
    }
    @Test
    @DisplayName("Find by ID - Should return benefit when found")
    void shouldReturnBenefitWhenFound() {
        Long benefitId = 1L;
        Benefit benefit = createBenefit(benefitId, "Vale Refeição", "Descrição", true);

        when(benefitRepository.findById(benefitId)).thenReturn(Optional.of(benefit));

        BenefitDTO result = benefitService.findById(benefitId);

        assertEquals(benefitId, result.getId());
        assertEquals("Vale Refeição", result.getName());
        verify(benefitRepository).findById(benefitId);
    }

    @Test
    @DisplayName("Find by ID - Should throw exception when benefit not found")
    void shouldThrowExceptionWhenBenefitNotFound() {
        Long nonExistingId = 99L;

        when(benefitRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> benefitService.findById(nonExistingId));
        verify(benefitRepository).findById(nonExistingId);
    }

    @Test
    @DisplayName("Find all - Should return all benefits")
    void shouldReturnAllBenefits() {
        List<Benefit> benefits = Arrays.asList(
                createBenefit(1L, "Vale Alimentação", "Descrição 1", true),
                createBenefit(2L, "Vale Transporte", "Descrição 2", true)
        );

        when(benefitRepository.findAll()).thenReturn(benefits);

        List<BenefitDTO> result = benefitService.findAll();

        assertEquals(2, result.size());
        assertEquals("Vale Alimentação", result.get(0).getName());
        assertEquals("Vale Transporte", result.get(1).getName());
        verify(benefitRepository).findAll();
    }

    @Test
    @DisplayName("Find all - Should return empty list when no benefits")
    void shouldReturnEmptyListWhenNoBenefits() {
        when(benefitRepository.findAll()).thenReturn(Collections.emptyList());

        List<BenefitDTO> result = benefitService.findAll();

        assertTrue(result.isEmpty());
        verify(benefitRepository).findAll();
    }
}