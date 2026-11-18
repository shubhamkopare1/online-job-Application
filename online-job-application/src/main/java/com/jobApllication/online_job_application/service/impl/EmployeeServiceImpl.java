package com.jobApllication.online_job_application.service.impl;

import com.jobApllication.online_job_application.dto.request.EmployeeRequest;
import com.jobApllication.online_job_application.dto.response.EmployeeResponse;
import com.jobApllication.online_job_application.model.Employee;
import com.jobApllication.online_job_application.model.User;
import com.jobApllication.online_job_application.repository.EmployeeRepository;
import com.jobApllication.online_job_application.repository.UserRepository;
import com.jobApllication.online_job_application.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Override
    public EmployeeResponse createProfile(
            String email,
            EmployeeRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (employeeRepository.existsByUserId(user.getId())) {
            throw new RuntimeException(
                    "Employee profile already exists");
        }

        Employee employee = Employee.builder()
                .user(user)
                .phone(request.getPhone())
                .address(request.getAddress())
                .city(request.getCity())
                .education(request.getEducation())
                .resumeUrl(request.getResumeUrl())
                .build();

        Employee saved = employeeRepository.save(employee);

        return mapToResponse(saved);
    }

    @Override
    public EmployeeResponse getMyProfile(String email) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        return mapToResponse(employee);
    }

    @Override
    public EmployeeResponse updateProfile(
            String email,
            EmployeeRequest request) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        employee.setPhone(request.getPhone());
        employee.setAddress(request.getAddress());
        employee.setCity(request.getCity());
        employee.setEducation(request.getEducation());
        employee.setResumeUrl(request.getResumeUrl());

        Employee updated =
                employeeRepository.save(employee);

        return mapToResponse(updated);
    }

    private EmployeeResponse mapToResponse(
            Employee employee) {

        User user = employee.getUser();

        return EmployeeResponse.builder()
                .id(employee.getId())
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(employee.getPhone())
                .address(employee.getAddress())
                .city(employee.getCity())
                .education(employee.getEducation())
                .resumeUrl(employee.getResumeUrl())
                .build();
    }
}
