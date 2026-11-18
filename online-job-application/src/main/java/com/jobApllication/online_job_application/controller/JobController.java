package com.jobApllication.online_job_application.controller;


import com.jobApllication.online_job_application.dto.request.JobRequest;
import com.jobApllication.online_job_application.dto.response.JobResponse;
import com.jobApllication.online_job_application.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            Authentication authentication,
            @Valid @RequestBody JobRequest request) {
          String email = authentication.getName();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        jobService.createJob(
                                email,
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {

        return ResponseEntity.ok(
                jobService.getAllJobs()
        );
    }

//    @GetMapping("/employer/{employerId}")
//    public ResponseEntity<List<JobResponse>>
//    getEmployerJobs(
//            @PathVariable UUID employerId) {
//
//        return ResponseEntity.ok(
//                jobService.getEmployerJobs(
//                        employerId
//                )
//        );
//    }
    @GetMapping("/my-jobs")
    public ResponseEntity<List<JobResponse>> getMyJobs(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                jobService.getMyJob(email)
        );
    }
    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable UUID jobId,
            Authentication authentication,
            @Valid @RequestBody JobRequest request) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                jobService.updateJob(
                        jobId,
                        email,
                        request
                )
        );
    }
    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable UUID jobId,
            Authentication authentication) {

        String email = authentication.getName();

        jobService.deleteJob(
                jobId,
                email
        );

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobResponse>> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String experience) {

        return ResponseEntity.ok(
                jobService.searchJobs(
                        keyword,
                        location,
                        jobType,
                        experience
                )
        );
    }
}