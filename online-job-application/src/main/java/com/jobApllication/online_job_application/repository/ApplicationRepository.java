package com.jobApllication.online_job_application.repository;

import com.jobApllication.online_job_application.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application,UUID> {

    List<Application> findByUserId(UUID userId);

    List<Application> findByJobId(UUID jobId);

    boolean existsByJobIdAndUserId(
            UUID jobId,
            UUID userId
    );


}
