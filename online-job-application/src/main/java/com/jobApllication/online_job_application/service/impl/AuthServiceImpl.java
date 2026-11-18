package com.jobApllication.online_job_application.service.impl;

import com.jobApllication.online_job_application.dto.request.LoginRequest;
import com.jobApllication.online_job_application.dto.request.RegisterRequest;
import com.jobApllication.online_job_application.dto.response.AuthResponse;
import com.jobApllication.online_job_application.model.User;
import com.jobApllication.online_job_application.repository.UserRepository;
import com.jobApllication.online_job_application.security.JwtService;
import com.jobApllication.online_job_application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public AuthResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("email already registerd");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
        .build();
        User savedUser = userRepository.save(user);
        return AuthResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().name())
                .message("Registration successfull")
                .build();
    }

    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("invalid email or password"));
        if(!passwordEncoder.matches(request.getPassword(),
                user.getPassword())){
            throw new RuntimeException("Invalid email or password");

        }
        String token = jwtService.generateToken(user.getEmail());
        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(token)
                .message("Login successful")
                .build();
    }

}
