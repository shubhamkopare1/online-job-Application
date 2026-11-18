package com.jobApllication.online_job_application.controller;


import com.jobApllication.online_job_application.dto.request.EmployeeRequest;
import com.jobApllication.online_job_application.dto.response.EmployeeResponse;
import com.jobApllication.online_job_application.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createProfile(
            Authentication authentication,
            @Valid @RequestBody EmployeeRequest request) {

        String email = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createProfile(email, request));
    }

    @GetMapping("/me")
    public ResponseEntity<EmployeeResponse> getMyProfile(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                employeeService.getMyProfile(email)
        );
    }

    @PutMapping("/me")
    public ResponseEntity<EmployeeResponse> updateProfile(
            Authentication authentication,
            @Valid @RequestBody EmployeeRequest request) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                employeeService.updateProfile(email, request)
        );
    }
}
