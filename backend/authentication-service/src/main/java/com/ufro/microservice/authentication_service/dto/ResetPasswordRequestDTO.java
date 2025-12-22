package com.ufro.microservice.authentication_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequestDTO {
    @NotBlank(message = "Cannot be blank")
    @Email(message = "Must be a valid email address")
    private String email;
    @NotBlank(message = "Cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String newPassword;

    public ResetPasswordRequestDTO() {
    }

    public ResetPasswordRequestDTO(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @NotBlank(message = "Cannot be blank") @Size(min = 8, message = "Password must be at least 8 characters long") String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotBlank(message = "Cannot be blank") @Size(min = 8, message = "Password must be at least 8 characters long") String newPassword) {
        this.newPassword = newPassword;
    }
}
