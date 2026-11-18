package com.jobApllication.online_job_application.service;


import com.jobApllication.online_job_application.dto.request.SkillRequest;
import com.jobApllication.online_job_application.dto.response.SkillResponse;

import java.util.List;
import java.util.UUID;

public interface SkillService {

    SkillResponse addSkill(
            String email,
            SkillRequest request
    );

    List<SkillResponse> getMySkills(
            String email
    );

    void deleteSkill(
            String email,
            UUID skillId
    );
}