package com.jobApllication.online_job_application.controller;


import com.jobApllication.online_job_application.dto.request.QualificationRequest;
import com.jobApllication.online_job_application.dto.response.QualificationResponse;
import com.jobApllication.online_job_application.service.QualificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/qualifications")
@RequiredArgsConstructor
public class QualificationController {

    private final QualificationService qualificationService;

    @PostMapping
    public ResponseEntity<QualificationResponse> addQualification(
            Authentication authentication,
            @Valid @RequestBody QualificationRequest request) {

        String email = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        qualificationService.addQualification(
                                email,
                                request
                        )
                );
    }

    @GetMapping("/my")
    public ResponseEntity<List<QualificationResponse>>
    getMyQualifications(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                qualificationService.getMyQualifications(email)
        );
    }

    @PutMapping("/{qualificationId}")
    public ResponseEntity<QualificationResponse>
    updateQualification(
            Authentication authentication,
            @PathVariable UUID qualificationId,
            @Valid @RequestBody QualificationRequest request) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                qualificationService.updateQualification(
                        email,
                        qualificationId,
                        request
                )
        );
    }


    @DeleteMapping("/{qualificationId}")
    public ResponseEntity<Void> deleteQualification(
            Authentication authentication,
            @PathVariable UUID qualificationId) {

        String email = authentication.getName();

        qualificationService.deleteQualification(
                email,
                qualificationId
        );

        return ResponseEntity.noContent().build();
    }
}