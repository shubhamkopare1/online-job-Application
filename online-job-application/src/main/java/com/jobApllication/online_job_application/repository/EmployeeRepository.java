package com.jobApllication.online_job_application.repository;

import com.jobApllication.online_job_application.model.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByUserId(UUID userId);

    Optional<Employee> findByUserEmail(String email);

    boolean existsByUserId(UUID userId);
}