package com.jobApllication.online_job_application.controller;


import com.jobApllication.online_job_application.dto.request.SkillRequest;
import com.jobApllication.online_job_application.dto.response.SkillResponse;
import com.jobApllication.online_job_application.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillResponse> addSkill(
            Authentication authentication,
            @Valid @RequestBody SkillRequest request) {

        String email = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(skillService.addSkill(email, request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<SkillResponse>> getMySkills(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                skillService.getMySkills(email)
        );
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(
            Authentication authentication,
            @PathVariable UUID skillId) {

        String email = authentication.getName();

        skillService.deleteSkill(email, skillId);

        return ResponseEntity.noContent().build();
    }
}