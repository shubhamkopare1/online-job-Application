package com.jobApllication.online_job_application.controller;

import com.jobApllication.online_job_application.dto.request.EmployerRequest;
import com.jobApllication.online_job_application.dto.response.EmployerResponse;
import com.jobApllication.online_job_application.service.EmployerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employers")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerService employerService;

    @PostMapping
    public ResponseEntity<EmployerResponse> createEmployer(
            Authentication authentication,
            @Valid @RequestBody EmployerRequest employerRequest) {

        String email = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employerService.createEmployer(
                        email,
                        employerRequest
                ));
    }

    @GetMapping("/me")
    public ResponseEntity<EmployerResponse> getMyEmployer(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                employerService.getEmployer(email)
        );
    }

    @PutMapping("/me")
    public ResponseEntity<EmployerResponse> updateEmployer(
            Authentication authentication,
            @Valid @RequestBody EmployerRequest employerRequest) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                employerService.updateEmployer(
                        email,
                        employerRequest
                )
        );
    }
}