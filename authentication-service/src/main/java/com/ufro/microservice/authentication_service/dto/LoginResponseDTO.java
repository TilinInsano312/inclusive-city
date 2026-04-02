package com.ufro.microservice.authentication_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginResponseDTO {
    @NotBlank(message = "Cannot be blank")
    @Size(min = 2, max = 65, message = "Username must be between 3 and 20 characters")
    private String username;
    @NotBlank(message = "Cannot be blank")
    @Email(message = "Must be a valid email address")
    private String email;

    public LoginResponseDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public @NotBlank(message = "Cannot be blank") @Size(min = 2, max = 65, message = "Username must be between 3 and 20 characters") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Cannot be blank") @Size(min = 2, max = 65, message = "Username must be between 3 and 20 characters") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Cannot be blank") @Email(message = "Must be a valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Cannot be blank") @Email(message = "Must be a valid email address") String email) {
        this.email = email;
    }
}
