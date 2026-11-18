package com.jobApllication.online_job_application.dto.response;


import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {

    private UUID id;

    private String title;

    private String description;

    private String location;

    private String jobType;

    private String experience;

    private Double salary;

    private UUID employerId;

    private String companyName;
}