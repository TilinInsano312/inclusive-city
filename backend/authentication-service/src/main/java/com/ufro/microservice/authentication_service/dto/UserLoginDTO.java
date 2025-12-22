package com.ufro.microservice.authentication_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginDTO {
    @NotBlank(message = "Cannot be blank")
    @Email
    private String email;
    @NotBlank(message = "Cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    public UserLoginDTO(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
