package com.corporate.benefits.benefit_api.controller;

import com.corporate.benefits.benefit_api.dto.AssignmentDTO;
import com.corporate.benefits.benefit_api.service.impl.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Assignments", description = "Benefit assignment operations")
@Validated
@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @Operation(summary = "Assign a benefit to an employee", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Benefit assigned"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> assignBenefit(@Valid @RequestBody AssignmentDTO dto) {
        assignmentService.assignBenefitToEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(@Valid @RequestBody AssignmentDTO dto) {
        return ResponseEntity.ok("Funcionou! EmployeeId: " + dto.getEmployeeId());
    }
}
