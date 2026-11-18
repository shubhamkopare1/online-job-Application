package com.jobApllication.online_job_application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

@Data
public class EmployerRequest {
    @NotBlank(message = "Company name is required")
    private String companyName;
    private String companyDescription;
    private String companyWebsite;
    private String location;
}
