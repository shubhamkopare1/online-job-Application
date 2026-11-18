package com.jobApllication.online_job_application.service;

import com.jobApllication.online_job_application.dto.request.LoginRequest;
import com.jobApllication.online_job_application.dto.request.RegisterRequest;
import com.jobApllication.online_job_application.dto.response.AuthResponse;

public interface  AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);


}
