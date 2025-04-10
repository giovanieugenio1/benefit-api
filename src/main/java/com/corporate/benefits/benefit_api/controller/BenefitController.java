package com.corporate.benefits.benefit_api.controller;

import com.corporate.benefits.benefit_api.dto.BenefitDTO;
import com.corporate.benefits.benefit_api.service.BenefitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@Validated
@RestController
@RequestMapping("/benefit")
@RequiredArgsConstructor
public class BenefitController {

    private final BenefitService benefitService;

    @PostMapping
    public ResponseEntity<BenefitDTO> create(@Valid @RequestBody BenefitDTO dto) {
        return new ResponseEntity<>(benefitService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BenefitDTO> update(@PathVariable("id") Long id, @Valid @RequestBody BenefitDTO dto) {
        return ResponseEntity.ok(benefitService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        benefitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BenefitDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ofNullable(benefitService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<BenefitDTO>> findAll() {
        return ResponseEntity.ok(benefitService.findAll());
    }
}
