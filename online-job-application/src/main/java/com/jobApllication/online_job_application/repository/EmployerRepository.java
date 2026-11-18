package com.jobApllication.online_job_application.repository;

import com.jobApllication.online_job_application.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;
public interface EmployerRepository
        extends JpaRepository<Employer, UUID> {

    Optional<Employer> findByUserId(UUID userId);

    Optional<Employer> findByUserEmail(String email);
    
    boolean existsByUserId(UUID userId);
}