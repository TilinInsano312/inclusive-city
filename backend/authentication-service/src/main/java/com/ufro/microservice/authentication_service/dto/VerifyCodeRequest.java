package com.ufro.microservice.authentication_service.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VerifyCodeRequest {
    @Nonnull
    @NotBlank
    @Email
    private String email;
    @Nonnull
    @NotBlank
    @Size(min = 6, max = 6, message = "Code must be exactly 6 characters long")
    private String code;

    public VerifyCodeRequest(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

}
