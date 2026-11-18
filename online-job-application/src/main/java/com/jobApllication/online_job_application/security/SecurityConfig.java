package com.jobApllication.online_job_application.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        // Employer APIs
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/jobs"
                        ).hasRole("EMPLOYER")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/jobs/my-jobs"
                        ).hasRole("EMPLOYER")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/jobs/**"
                        ).hasRole("EMPLOYER")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/jobs/**"
                        ).hasRole("EMPLOYER")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/applications/job/**"
                        ).hasRole("EMPLOYER")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/applications/*/status"
                        ).hasRole("EMPLOYER")

                        // Employee APIs
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/applications"
                        ).hasRole("EMPLOYEE")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/applications/my"
                        ).hasRole("EMPLOYEE")

                        .requestMatchers("/api/qualifications/**")
                        .hasRole("EMPLOYEE")

                        .requestMatchers("/api/work-experiences/**")
                        .hasRole("EMPLOYEE")

                        .requestMatchers("/api/employees/**")
                                .hasRole("EMPLOYEE")
                        // Everything else requires JWT
                        .anyRequest().authenticated()
                )

                // JWT filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}