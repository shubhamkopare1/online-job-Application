package com.jobApllication.online_job_application.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobRequest {

    @NotBlank(message = "Job title is required")
    private String title;

    @NotBlank(message = "Job description is required")
    private String description;

    private String location;

    private String jobType;

    private String experience;

    private Double salary;
}