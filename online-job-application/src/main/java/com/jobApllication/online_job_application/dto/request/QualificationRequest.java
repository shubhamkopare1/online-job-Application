package com.jobApllication.online_job_application.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QualificationRequest {

    @NotBlank(message = "Degree is required")
    private String degree;

    private String institution;

    private String fieldOfStudy;

    private String startYear;

    private String endYear;
}