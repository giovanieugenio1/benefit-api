package com.corporate.benefits.benefit_api.controller;

import com.corporate.benefits.benefit_api.dto.BenefitDTO;
import com.corporate.benefits.benefit_api.dto.EmployeeDTO;
import com.corporate.benefits.benefit_api.entities.Employee;
import com.corporate.benefits.benefit_api.mapper.BenefitMapper;
import com.corporate.benefits.benefit_api.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Employee", description = "Contains all operations related to employee resources")
@Validated
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Create a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
    })
    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeDTO dto) {
        return new ResponseEntity<>(employeeService.create(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable("id") Long id, @Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.update(id, dto));
    }

    @Operation(summary = "Delete an employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Find an employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ofNullable(employeeService.findById(id));
    }

    @Operation(summary = "List all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @Operation(
            summary = "Get benefits assigned to an employee",
            description = "Returns a list of benefits assigned to the employee with the provided ID. Only accessible to users with roles ADMIN or USER.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of benefits returned successfully",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = BenefitDTO.class)))
                    ),
                    @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            }
    )
    @GetMapping("/{id}/benefits")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<List<BenefitDTO>> getBenefits(@PathVariable("id") Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        List<BenefitDTO> dto = employee.getBenefits()
                .stream()
                .map(BenefitMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dto);
    }
}
