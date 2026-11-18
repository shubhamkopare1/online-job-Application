package com.jobApllication.online_job_application.controller;

import com.jobApllication.online_job_application.dto.request.WorkExperienceRequest;
import com.jobApllication.online_job_application.dto.response.WorkExperienceResponse;
import com.jobApllication.online_job_application.service.WorkExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/work-experiences")
@RequiredArgsConstructor
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @PostMapping
    public ResponseEntity<WorkExperienceResponse> addExperience(
            Authentication authentication,
            @Valid @RequestBody WorkExperienceRequest request) {

        String email = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        workExperienceService.addExperience(
                                email,
                                request
                        )
                );
    }

    @GetMapping("/my")
    public ResponseEntity<List<WorkExperienceResponse>>
    getMyExperiences(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                workExperienceService.getMyExperiences(email)
        );
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<WorkExperienceResponse>
    updateExperience(
            Authentication authentication,
            @PathVariable UUID experienceId,
            @Valid @RequestBody WorkExperienceRequest request) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                workExperienceService.updateExperience(
                        email,
                        experienceId,
                        request
                )
        );
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<Void> deleteExperience(
            Authentication authentication,
            @PathVariable UUID experienceId) {

        String email = authentication.getName();

        workExperienceService.deleteExperience(
                email,
                experienceId
        );

        return ResponseEntity.noContent().build();
    }
}