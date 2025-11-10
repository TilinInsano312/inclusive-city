package com.ufro.microservice.authentication_service.service;

import com.ufro.microservice.authentication_service.dto.*;

public interface IAuthService {
    UserCrendentialDTO registerUser(UserCrendentialDTO userCrendentialDTO);
    LoginResponseDTO loginUser(UserLoginDTO userLoginDTO);
    long resetPassword(ResetPasswordRequestDTO resetPasswordDTO);
    EmailDTO sendResetEmail(EmailDTO emailRequestDTO);
}
