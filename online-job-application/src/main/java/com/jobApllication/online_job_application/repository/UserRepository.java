package com.jobApllication.online_job_application.repository;

import com.jobApllication.online_job_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User,UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
