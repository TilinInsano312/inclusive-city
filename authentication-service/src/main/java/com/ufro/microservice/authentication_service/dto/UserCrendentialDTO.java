package com.ufro.microservice.authentication_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCrendentialDTO {
    @NotBlank(message = "Cannot be blank")
    @Size(min = 2, max = 65, message = "Username must be between 3 and 20 characters")
    private String username;
    private String firebaseUid;
    @NotBlank(message = "Cannot be blank")
    @Email(message = "Must be a valid email address")
    private String email;

    public UserCrendentialDTO(String username, String firebaseUid, String email) {
        this.username = username;
        this.firebaseUid = firebaseUid;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
