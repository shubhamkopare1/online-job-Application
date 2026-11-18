package com.jobApllication.online_job_application.repository;


import com.jobApllication.online_job_application.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

    List<Job> findByEmployerId(UUID employerId);

    @Query("""
            SELECT j FROM Job j
            WHERE
            (:keyword IS NULL OR
             LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
             LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND
            (:location IS NULL OR
             LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))
            AND
            (:jobType IS NULL OR
             LOWER(j.jobType) = LOWER(:jobType))
            AND
            (:experience IS NULL OR
             LOWER(j.experience) = LOWER(:experience))
            """)
    List<Job> searchJobs(
            @Param("keyword") String keyword,
            @Param("location") String location,
            @Param("jobType") String jobType,
            @Param("experience") String experience
    );
}
