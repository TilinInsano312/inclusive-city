package com.ufro.microservice.authentication_service.controller;

import com.google.firebase.auth.FirebaseToken;
import com.ufro.microservice.authentication_service.common.response.ApiResponse;
import com.ufro.microservice.authentication_service.dto.*;
import com.ufro.microservice.authentication_service.model.User;
import com.ufro.microservice.authentication_service.service.IAuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("inclusive/api/v1/account/")
public class AuthController {

    private final IAuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("auth/register")
    public ResponseEntity<ApiResponse<UserCrendentialDTO>> registerUser(@RequestBody @Valid UserCrendentialDTO userCrendentialDTO) {
        return ResponseEntity.status(201).body(new ApiResponse<>(authService.registerUser(userCrendentialDTO)));
    }

    @PostMapping("auth/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> loginUser(@AuthenticationPrincipal User firebaseToken) {
        log.info("Login attempt for user: {}", firebaseToken.getEmail());
        LoginResponseDTO loginResponseDTO = authService.loginUser(firebaseToken.getEmail());
        log.info("User details: {}", loginResponseDTO);
        ApiResponse<LoginResponseDTO> response = new ApiResponse<>(loginResponseDTO);
        return ResponseEntity.ok(response);

    }

}
