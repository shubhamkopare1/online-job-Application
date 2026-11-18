package com.jobApllication.online_job_application.service.impl;


import com.jobApllication.online_job_application.dto.request.SkillRequest;
import com.jobApllication.online_job_application.dto.response.SkillResponse;
import com.jobApllication.online_job_application.model.Employee;
import com.jobApllication.online_job_application.model.Skill;
import com.jobApllication.online_job_application.repository.EmployeeRepository;
import com.jobApllication.online_job_application.repository.SkillRepository;
import com.jobApllication.online_job_application.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class
SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public SkillResponse addSkill(
            String email,
            SkillRequest request) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        if (skillRepository.existsByEmployeeIdAndName(
                employee.getId(),
                request.getName())) {

            throw new RuntimeException(
                    "Skill already exists");
        }

        Skill skill = Skill.builder()
                .name(request.getName())
                .employee(employee)
                .build();

        Skill saved = skillRepository.save(skill);

        return mapToResponse(saved);
    }

    @Override
    public List<SkillResponse> getMySkills(
            String email) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        return skillRepository
                .findByEmployeeId(employee.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteSkill(
            String email,
            UUID skillId) {

        Employee employee =
                employeeRepository.findByUserEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee profile not found"));

        Skill skill =
                skillRepository.findById(skillId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Skill not found"));

        if (!skill.getEmployee().getId()
                .equals(employee.getId())) {

            throw new RuntimeException(
                    "You are not allowed to delete this skill");
        }

        skillRepository.delete(skill);
    }

    private SkillResponse mapToResponse(
            Skill skill) {

        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}