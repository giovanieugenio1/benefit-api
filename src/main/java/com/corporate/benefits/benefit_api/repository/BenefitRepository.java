package com.corporate.benefits.benefit_api.repository;

import com.corporate.benefits.benefit_api.entities.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {

    Optional<Benefit> findByName(String name);
}
