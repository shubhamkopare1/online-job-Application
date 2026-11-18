package com.jobApllication.online_job_application.service.impl;

import com.jobApllication.online_job_application.dto.request.ApplicationRequest;
import com.jobApllication.online_job_application.dto.response.ApplicationResponse;
import com.jobApllication.online_job_application.model.Application;
import com.jobApllication.online_job_application.model.Employer;
import com.jobApllication.online_job_application.model.Job;
import com.jobApllication.online_job_application.model.User;
import com.jobApllication.online_job_application.model.enums.ApplicationStatus;
import com.jobApllication.online_job_application.repository.ApplicationRepository;
import com.jobApllication.online_job_application.repository.EmployerRepository;
import com.jobApllication.online_job_application.repository.JobRepository;
import com.jobApllication.online_job_application.repository.UserRepository;
import com.jobApllication.online_job_application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;

    @Override
    public ApplicationResponse applyForJob(String email, ApplicationRequest request) {
        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );
        Job job =
                jobRepository.findById(request.getJobId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found"
                                )
                        );
        boolean alreadyApplied =
                applicationRepository
                        .existsByJobIdAndUserId(
                                job.getId(),
                                user.getId()
                        );

        if (alreadyApplied) {
            throw new RuntimeException(
                    "You have already applied for this job"
            );
        }

        Application application =
                Application.builder()
                        .job(job)
                        .user(user)
                        .status(ApplicationStatus.APPLIED)
                        .build();

        Application saved =
                applicationRepository.save(application);

        return mapToResponse(saved);
    }

    @Override
    public List<ApplicationResponse> getMyApplications(
            String email) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        return applicationRepository
                .findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ApplicationResponse> getJobApplications(
            String email,
            UUID jobId) {

        Employer employer =
                employerRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employer profile not found"
                                )
                        );

        Job job =
                jobRepository.findById(jobId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Job not found"
                                )
                        );

        if (!job.getEmployer().getId()
                .equals(employer.getId())) {

            throw new RuntimeException(
                    "You are not allowed to view these applications"
            );
        }

        return applicationRepository
                .findByJobId(jobId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public ApplicationResponse updateApplicationStatus(
            String email,
            UUID applicationId,
            ApplicationStatus status) {

        Employer employer =
                employerRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employer profile not found"
                                )
                        );

        Application application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                )
                        );

        Job job = application.getJob();

        if (!job.getEmployer().getId()
                .equals(employer.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this application"
            );
        }

        application.setStatus(status);

        Application updated =
                applicationRepository.save(application);

        return mapToResponse(updated);
    }

    private ApplicationResponse mapToResponse(
            Application application) {

        return ApplicationResponse.builder()
                .id(application.getId())
                .jobId(application.getJob().getId())
                .jobTitle(application.getJob().getTitle())
                .userId(application.getUser().getId())
                .userName(application.getUser().getName())
                .userEmail(application.getUser().getEmail())
                .status(application.getStatus())
                .build();
    }
}
