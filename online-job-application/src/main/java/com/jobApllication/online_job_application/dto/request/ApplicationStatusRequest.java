package com.jobApllication.online_job_application.dto.request;

import com.jobApllication.online_job_application.model.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

    @Data
    public class ApplicationStatusRequest {

        @NotNull
        private ApplicationStatus status;
    }
