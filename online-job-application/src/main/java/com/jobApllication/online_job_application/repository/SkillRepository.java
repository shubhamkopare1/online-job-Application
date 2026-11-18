package com.jobApllication.online_job_application.repository;


import com.jobApllication.online_job_application.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skill, UUID> {

    List<Skill> findByEmployeeId(UUID employeeId);

    boolean existsByEmployeeIdAndName(
            UUID employeeId,
            String name
    );
}