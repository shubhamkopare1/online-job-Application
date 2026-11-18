package com.jobApllication.online_job_application.service;

import com.jobApllication.online_job_application.dto.request.QualificationRequest;
import com.jobApllication.online_job_application.dto.response.QualificationResponse;

import java.util.List;
import java.util.UUID;

public interface QualificationService {

    QualificationResponse addQualification(
            String email,
            QualificationRequest request
    );

    List<QualificationResponse> getMyQualifications(
            String email
    );

    QualificationResponse updateQualification(
            String email,
            UUID qualificationId,
            QualificationRequest request
    );

    void deleteQualification(
            String email,
            UUID qualificationId
    );
}
