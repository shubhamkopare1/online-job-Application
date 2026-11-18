package com.jobApllication.online_job_application.service;

import com.jobApllication.online_job_application.dto.request.ApplicationRequest;
import com.jobApllication.online_job_application.dto.response.ApplicationResponse;
import com.jobApllication.online_job_application.model.enums.ApplicationStatus;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {
    ApplicationResponse applyForJob(String email, ApplicationRequest request);
    List<ApplicationResponse> getMyApplications(
            String email
    );
    List<ApplicationResponse> getJobApplications(
            String email,
            UUID jobId
    );
    ApplicationResponse updateApplicationStatus(
            String email,
            UUID applicationId,
            ApplicationStatus status
    );

}
