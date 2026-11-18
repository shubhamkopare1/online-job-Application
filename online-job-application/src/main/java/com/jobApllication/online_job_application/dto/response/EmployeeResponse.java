package com.jobApllication.online_job_application.dto.response;


import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private UUID id;
    private UUID userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String education;
    private String resumeUrl;

}