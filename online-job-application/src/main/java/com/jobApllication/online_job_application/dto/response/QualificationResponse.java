package com.jobApllication.online_job_application.dto.response;


import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualificationResponse {

    private UUID id;
    private String degree;
    private String institution;
    private String fieldOfStudy;
    private String startYear;
    private String endYear;
}