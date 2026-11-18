package com.jobApllication.online_job_application.dto.request;

import com.jobApllication.online_job_application.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "enter a valid email")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "email is required")
    @Size(min=6 , message = "password must contain at least 6 character ")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;

}
