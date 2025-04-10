package com.corporate.benefits.benefit_api.controller;

import com.corporate.benefits.benefit_api.dto.BenefitDTO;
import com.corporate.benefits.benefit_api.service.BenefitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Benefit", description = "Contains all operations related to Benefit resources")
@Validated
@RestController
@RequestMapping("/benefit")
@RequiredArgsConstructor
public class BenefitController {

    private final BenefitService benefitService;

    @Operation(summary = "Create a new benefit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Benefit created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BenefitDTO> create(@Valid @RequestBody BenefitDTO dto) {
        return new ResponseEntity<>(benefitService.create(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing benefit by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Benefit updated successfully"),
            @ApiResponse(responseCode = "404", description = "Benefit not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BenefitDTO> update(@PathVariable("id") Long id, @Valid @RequestBody BenefitDTO dto) {
        return ResponseEntity.ok(benefitService.update(id, dto));
    }

    @Operation(summary = "Delete an benefit by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Benefit deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Benefit not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        benefitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Find an benefit by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Benefit found"),
            @ApiResponse(responseCode = "404", description = "Benefit not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BenefitDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ofNullable(benefitService.findById(id));
    }

    @Operation(summary = "List all benefits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Benefits retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<BenefitDTO>> findAll() {
        return ResponseEntity.ok(benefitService.findAll());
    }
}
