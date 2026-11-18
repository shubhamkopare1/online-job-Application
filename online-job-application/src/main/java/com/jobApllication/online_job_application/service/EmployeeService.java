package com.jobApllication.online_job_application.service;


import com.jobApllication.online_job_application.dto.request.EmployeeRequest;
import com.jobApllication.online_job_application.dto.response.EmployeeResponse;

public interface EmployeeService {

    EmployeeResponse createProfile(
            String email,
            EmployeeRequest request
    );

    EmployeeResponse getMyProfile(String email);

    EmployeeResponse updateProfile(
            String email,
            EmployeeRequest request
    );
}
