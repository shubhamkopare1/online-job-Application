package com.jobApllication.online_job_application.service.impl;


import com.jobApllication.online_job_application.dto.request.WorkExperienceRequest;
import com.jobApllication.online_job_application.dto.response.WorkExperienceResponse;
import com.jobApllication.online_job_application.model.Employee;
import com.jobApllication.online_job_application.model.WorkExperience;
import com.jobApllication.online_job_application.repository.EmployeeRepository;
import com.jobApllication.online_job_application.repository.WorkExperienceRepository;
import com.jobApllication.online_job_application.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl
        implements WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public WorkExperienceResponse addExperience(
            String email,
            WorkExperienceRequest request) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        WorkExperience experience =
                WorkExperience.builder()
                        .companyName(request.getCompanyName())
                        .position(request.getPosition())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .currentlyWorking(
                                request.isCurrentlyWorking())
                        .description(request.getDescription())
                        .employee(employee)
                        .build();

        WorkExperience saved =
                workExperienceRepository.save(experience);

        return mapToResponse(saved);
    }

    @Override
    public List<WorkExperienceResponse> getMyExperiences(
            String email) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        return workExperienceRepository
                .findByEmployeeId(employee.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public WorkExperienceResponse updateExperience(
            String email,
            UUID experienceId,
            WorkExperienceRequest request) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        WorkExperience experience =
                workExperienceRepository.findById(experienceId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Work experience not found"));

        if (!experience.getEmployee().getId()
                .equals(employee.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this experience");
        }

        experience.setCompanyName(
                request.getCompanyName());

        experience.setPosition(
                request.getPosition());

        experience.setStartDate(
                request.getStartDate());

        experience.setEndDate(
                request.getEndDate());

        experience.setCurrentlyWorking(
                request.isCurrentlyWorking());

        experience.setDescription(
                request.getDescription());

        WorkExperience updated =
                workExperienceRepository.save(experience);

        return mapToResponse(updated);
    }

    @Override
    public void deleteExperience(
            String email,
            UUID experienceId) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        WorkExperience experience =
                workExperienceRepository.findById(experienceId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Work experience not found"));

        if (!experience.getEmployee().getId()
                .equals(employee.getId())) {

            throw new RuntimeException(
                    "You are not allowed to delete this experience");
        }

        workExperienceRepository.delete(experience);
    }

    private WorkExperienceResponse mapToResponse(
            WorkExperience experience) {

        return WorkExperienceResponse.builder()
                .id(experience.getId())
                .companyName(experience.getCompanyName())
                .position(experience.getPosition())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .currentlyWorking(
                        experience.isCurrentlyWorking())
                .description(experience.getDescription())
                .build();
    }
}
