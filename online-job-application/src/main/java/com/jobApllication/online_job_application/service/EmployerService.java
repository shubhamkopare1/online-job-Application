package com.jobApllication.online_job_application.service;

import com.jobApllication.online_job_application.dto.request.EmployerRequest;
import com.jobApllication.online_job_application.dto.response.EmployerResponse;

public interface EmployerService {

    EmployerResponse createEmployer(
            String email,
            EmployerRequest request
    );

    EmployerResponse getEmployer(
            String email
    );

    EmployerResponse updateEmployer(
            String email,
            EmployerRequest request
    );
}