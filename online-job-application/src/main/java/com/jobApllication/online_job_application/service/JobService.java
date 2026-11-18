package com.jobApllication.online_job_application.service;


import com.jobApllication.online_job_application.dto.request.JobRequest;
import com.jobApllication.online_job_application.dto.response.JobResponse;

import java.util.List;
import java.util.UUID;

public interface JobService {

    JobResponse createJob(
            String email,
            JobRequest request
    );
    List<JobResponse> searchJobs(
            String keyword,
            String location,
            String jobType,
            String experience
    );
    List<JobResponse> getMyJob(String email);
    List<JobResponse> getAllJobs();
    JobResponse updateJob(UUID jobId, String email, JobRequest request);
    void deleteJob(UUID jobid, String email);
//    List<JobResponse> getEmployerJobs(
//            UUID employerId
//    );

}