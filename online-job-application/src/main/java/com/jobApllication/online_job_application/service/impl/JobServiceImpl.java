package com.jobApllication.online_job_application.service.impl;

import com.jobApllication.online_job_application.dto.request.JobRequest;
import com.jobApllication.online_job_application.dto.response.JobResponse;
import com.jobApllication.online_job_application.model.Employer;
import com.jobApllication.online_job_application.model.Job;
import com.jobApllication.online_job_application.repository.EmployerRepository;
import com.jobApllication.online_job_application.repository.JobRepository;
import com.jobApllication.online_job_application.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;

    @Override
    public JobResponse createJob(
            String email,
            JobRequest request) {

        Employer employer =
                employerRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employer profile not found"
                                )
                        );

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .jobType(request.getJobType())
                .experience(request.getExperience())
                .salary(request.getSalary())
                .employer(employer)
                .build();

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    @Override
    public List<JobResponse> getAllJobs() {

        return jobRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public Page<JobResponse> getJobWithPagination(Pageable pageable){
         return jobRepository.findAll(pageable)
                 .map(this::mapToResponse);
    }

//    @Override
//    public List<JobResponse> getEmployerJobs(
//            UUID employerId) {
//
//        return jobRepository
//                .findByEmployerId(employerId)
//                .stream()
//                .map(this::mapToResponse)
//                .toList();
//    }

    @Override
    public List<JobResponse> getMyJob(String email) {

        Employer employer =
                employerRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employer profile not found"
                                )
                        );

        return jobRepository
                .findByEmployerId(employer.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public JobResponse updateJob(
            UUID jobId,
            String email,
            JobRequest request) {

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
                    "You are not allowed to update this job"
            );
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setExperience(request.getExperience());
        job.setSalary(request.getSalary());

        Job updatedJob = jobRepository.save(job);

        return mapToResponse(updatedJob);
    }
    @Override
    public List<JobResponse> searchJobs(
            String keyword,
            String location,
            String jobType,
            String experience) {

        return jobRepository.searchJobs(
                        keyword,
                        location,
                        jobType,
                        experience
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public void deleteJob(
            UUID jobId,
            String email) {

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
                    "You are not allowed to delete this job"
            );
        }

        jobRepository.delete(job);
    }
    private JobResponse mapToResponse(Job job) {

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .jobType(job.getJobType())
                .experience(job.getExperience())
                .salary(job.getSalary())
                .employerId(job.getEmployer().getId())
                .companyName(
                        job.getEmployer().getCompanyName()
                )
                .build();
    }



}