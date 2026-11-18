package com.jobApllication.online_job_application.dto.response;

import com.jobApllication.online_job_application.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {
    private UUID id;

    private UUID jobId;

    private String jobTitle;

    private UUID userId;

    private String userName;

    private String userEmail;

    private ApplicationStatus status;
}
