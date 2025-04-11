package com.corporate.benefits.benefit_api.controller;

import com.corporate.benefits.benefit_api.dto.AssignmentDTO;
import com.corporate.benefits.benefit_api.service.impl.AssignmentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> assignBenefit(@Valid @RequestBody AssignmentDTO dto) {
        assignmentService.assignBenefitToEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
