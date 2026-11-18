package com.jobApllication.online_job_application.controller;

import com.jobApllication.online_job_application.dto.request.ApplicationRequest;
import com.jobApllication.online_job_application.dto.request.ApplicationStatusRequest;
import com.jobApllication.online_job_application.dto.response.ApplicationResponse;
import com.jobApllication.online_job_application.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> applyForJob(Authentication authentication, @Valid @RequestBody ApplicationRequest request) {
        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(
                applicationService.applyForJob(email, request)
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationResponse>>
    getMyApplications(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                applicationService.getMyApplications(email)
        );
    }


    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>>
    getJobApplications(
            Authentication authentication,
            @PathVariable UUID jobId) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                applicationService.getJobApplications(
                        email,
                        jobId
                )
        );
    }
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationResponse>
    updateApplicationStatus(
            Authentication authentication,
            @PathVariable UUID applicationId,
            @Valid @RequestBody ApplicationStatusRequest request) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                applicationService.updateApplicationStatus(
                        email,
                        applicationId,
                        request.getStatus()
                )
        );
    }
}