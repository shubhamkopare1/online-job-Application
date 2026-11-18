package com.jobApllication.online_job_application.repository;


import com.jobApllication.online_job_application.model.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkExperienceRepository
        extends JpaRepository<WorkExperience, UUID> {

    List<WorkExperience> findByEmployeeId(UUID employeeId);
}