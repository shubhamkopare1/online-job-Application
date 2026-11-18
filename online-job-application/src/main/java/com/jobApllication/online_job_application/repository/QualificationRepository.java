package com.jobApllication.online_job_application.repository;

import com.jobApllication.online_job_application.model.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QualificationRepository
        extends JpaRepository<Qualification, UUID> {

    List<Qualification> findByEmployeeId(UUID employeeId);
}
