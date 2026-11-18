package com.jobApllication.online_job_application.service.impl;


import com.jobApllication.online_job_application.dto.request.EmployerRequest;
import com.jobApllication.online_job_application.dto.response.EmployerResponse;
import com.jobApllication.online_job_application.model.Employer;
import com.jobApllication.online_job_application.model.User;
import com.jobApllication.online_job_application.repository.EmployerRepository;
import com.jobApllication.online_job_application.repository.UserRepository;
import com.jobApllication.online_job_application.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;
    private final UserRepository userRepository;

    @Override
    public EmployerResponse createEmployer(
            String email,
            EmployerRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (employerRepository.existsByUserId(user.getId())) {
            throw new RuntimeException(
                    "Employer profile already exists");
        }

        Employer employer = Employer.builder()
                .user(user)
                .companyName(request.getCompanyName())
                .companyDescription(request.getCompanyDescription())
                .companyWebsite(request.getCompanyWebsite())
                .location(request.getLocation())
                .build();

        Employer saved = employerRepository.save(employer);

        return mapToResponse(saved);
    }

    @Override
    public EmployerResponse getEmployer(String email) {

        Employer employer =
                employerRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employer profile not found"));

        return mapToResponse(employer);
    }

    @Override
    public EmployerResponse updateEmployer(
            String email,
            EmployerRequest request) {

        Employer employer =
                employerRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employer profile not found"));

        employer.setCompanyName(request.getCompanyName());
        employer.setCompanyDescription(
                request.getCompanyDescription()
        );
        employer.setCompanyWebsite(
                request.getCompanyWebsite()
        );
        employer.setLocation(request.getLocation());

        Employer updated =
                employerRepository.save(employer);

        return mapToResponse(updated);
    }

    private EmployerResponse mapToResponse(
            Employer employer) {

        User user = employer.getUser();

        return EmployerResponse.builder()
                .id(employer.getId())
                .userId(user.getId())
                .companyDescription(
                        employer.getCompanyDescription()
                )
                .companyWebsite(
                        employer.getCompanyWebsite()
                )
                .location(
                        employer.getLocation()
                )
                .companyName(employer.getCompanyName())
                .build();
    }
}