package com.corporate.benefits.benefit_api.repository;

import com.corporate.benefits.benefit_api.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByCpf(String cpf);
    Optional<Employee> findByEmail(String email);

    @Query(value = """
        SELECT e.*
        FROM employees e
        LEFT JOIN benefits b ON b.employee_id = e.id
        WHERE b.id IS NULL
        """, nativeQuery = true)
    List<Employee> findEmployeeWithoutBenefit();
}
