package com.ufro.microservice.authentication_service.controller;

import com.ufro.microservice.authentication_service.common.response.ApiResponse;
import com.ufro.microservice.authentication_service.dto.*;
import com.ufro.microservice.authentication_service.service.IAuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<LoginResponseDTO>> loginUser(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        LoginResponseDTO loginResponseDTO = authService.loginUser(userLoginDTO);
        ApiResponse<LoginResponseDTO> response = new ApiResponse<>(loginResponseDTO);
        return ResponseEntity.ok(response);

    }
    @PatchMapping("reset-password")
    public ResponseEntity<ApiResponse<Long>> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO resetPasswordDTO) {
        long result = authService.resetPassword(resetPasswordDTO);
        log.info("Password reset result for user {}: {}", resetPasswordDTO.getEmail(), result);
        ApiResponse<Long> response = new ApiResponse<>(result);
        return ResponseEntity.ok(response);
    }
    @PostMapping("verify-code")
    public ResponseEntity<ApiResponse<String>> verifyCode(@RequestBody VerifyCodeRequest verifyCodeRequest) {
        boolean isValid = authService.verifyCode(verifyCodeRequest);
        if (isValid) {
            return ResponseEntity.ok(new ApiResponse<>("Codigo valido"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("Codigo Invalido o expirado"));
        }
    }


    @PostMapping("email/reset-password")
    public ResponseEntity<ApiResponse<String>> sendResetPassword(@RequestBody EmailDTO email) {
        ApiResponse<String> response = new ApiResponse<>(authService.sendResetEmail(email).getEmail());
        return ResponseEntity.ok(response);
    }

}
