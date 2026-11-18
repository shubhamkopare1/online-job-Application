package com.jobApllication.online_job_application.service.impl;


import com.jobApllication.online_job_application.dto.request.QualificationRequest;
import com.jobApllication.online_job_application.dto.response.QualificationResponse;
import com.jobApllication.online_job_application.model.Employee;
import com.jobApllication.online_job_application.model.Qualification;
import com.jobApllication.online_job_application.repository.EmployeeRepository;
import com.jobApllication.online_job_application.repository.QualificationRepository;
import com.jobApllication.online_job_application.service.QualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QualificationServiceImpl
        implements QualificationService {

    private final QualificationRepository qualificationRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public QualificationResponse addQualification(
            String email,
            QualificationRequest request) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        Qualification qualification =
                Qualification.builder()
                        .degree(request.getDegree())
                        .institution(request.getInstitution())
                        .fieldOfStudy(request.getFieldOfStudy())
                        .startYear(request.getStartYear())
                        .endYear(request.getEndYear())
                        .employee(employee)
                        .build();

        Qualification saved =
                qualificationRepository.save(qualification);

        return mapToResponse(saved);
    }

    @Override
    public List<QualificationResponse> getMyQualifications(
            String email) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        return qualificationRepository
                .findByEmployeeId(employee.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public QualificationResponse updateQualification(
            String email,
            UUID qualificationId,
            QualificationRequest request) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        Qualification qualification =
                qualificationRepository.findById(qualificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Qualification not found"));

        if (!qualification.getEmployee().getId()
                .equals(employee.getId())) {

            throw new RuntimeException(
                    "You are not allowed to update this qualification");
        }

        qualification.setDegree(request.getDegree());
        qualification.setInstitution(request.getInstitution());
        qualification.setFieldOfStudy(request.getFieldOfStudy());
        qualification.setStartYear(request.getStartYear());
        qualification.setEndYear(request.getEndYear());

        Qualification updated =
                qualificationRepository.save(qualification);

        return mapToResponse(updated);
    }

    @Override
    public void deleteQualification(
            String email,
            UUID qualificationId) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        Qualification qualification =
                qualificationRepository.findById(qualificationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Qualification not found"));

        if (!qualification.getEmployee().getId()
                .equals(employee.getId())) {

            throw new RuntimeException(
                    "You are not allowed to delete this qualification");
        }

        qualificationRepository.delete(qualification);
    }

    private QualificationResponse mapToResponse(
            Qualification qualification) {

        return QualificationResponse.builder()
                .id(qualification.getId())
                .degree(qualification.getDegree())
                .institution(qualification.getInstitution())
                .fieldOfStudy(qualification.getFieldOfStudy())
                .startYear(qualification.getStartYear())
                .endYear(qualification.getEndYear())
                .build();
    }
}