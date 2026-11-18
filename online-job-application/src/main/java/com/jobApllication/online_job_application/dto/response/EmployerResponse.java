package com.jobApllication.online_job_application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployerResponse {
    private UUID id;
    private UUID userId;
    private String companyDescription;
    private String companyWebsite;
    private String companyName;
    private String location;
}
