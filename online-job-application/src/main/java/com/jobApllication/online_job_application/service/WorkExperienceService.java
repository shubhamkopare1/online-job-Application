package com.jobApllication.online_job_application.service;


import com.jobApllication.online_job_application.dto.request.WorkExperienceRequest;
import com.jobApllication.online_job_application.dto.response.WorkExperienceResponse;

import java.util.List;
import java.util.UUID;

public interface WorkExperienceService {

    WorkExperienceResponse addExperience(
            String email,
            WorkExperienceRequest request
    );

    List<WorkExperienceResponse> getMyExperiences(
            String email
    );

    WorkExperienceResponse updateExperience(
            String email,
            UUID experienceId,
            WorkExperienceRequest request
    );

    void deleteExperience(
            String email,
            UUID experienceId
    );
}