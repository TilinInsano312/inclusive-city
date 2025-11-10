package com.ufro.microservice.authentication_service.service.impl;

import com.ufro.microservice.authentication_service.config.jwt.JwtUtils;
import com.ufro.microservice.authentication_service.dto.*;
import com.ufro.microservice.authentication_service.exceptions.RegisterConflictException;
import com.ufro.microservice.authentication_service.mapper.IUserMapper;
import com.ufro.microservice.authentication_service.model.User;
import com.ufro.microservice.authentication_service.repository.IUserCrendentialRepository;
import com.ufro.microservice.authentication_service.service.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final IUserCrendentialRepository userCrendentialRepository;
    private final IUserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(IUserCrendentialRepository userCrendentialRepository, IUserMapper userMapper, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userCrendentialRepository = userCrendentialRepository;
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserCrendentialDTO registerUser(UserCrendentialDTO userCrendentialDTO) {
        if (userCrendentialRepository.existsByEmail(userCrendentialDTO.getEmail())) {
            throw new RegisterConflictException("Email already exists");
        }
        userCrendentialDTO.setPassword(passwordEncoder.encode(userCrendentialDTO.getPassword()));
        return userMapper.registerToDTO(userCrendentialRepository.save(userMapper.registerToModel(userCrendentialDTO)));
    }

    @Override
    public LoginResponseDTO loginUser(UserLoginDTO userLoginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtUtils.generateToken(user);
        String refreshToken = jwtUtils.refreshToken(user);

        return new LoginResponseDTO(accessToken, refreshToken);
    }

    @Override
    public long resetPassword(ResetPasswordRequestDTO resetPasswordDTO) {
        return userCrendentialRepository.updateUserByEmail(resetPasswordDTO.getEmail(), resetPasswordDTO.getNewPassword());
    }

    @Override
    public EmailDTO sendResetEmail(EmailDTO emailRequestDTO) { //Por terminar @BerAxz
        if (!(userCrendentialRepository.existsByEmail(emailRequestDTO.getEmail()))) {
            throw new RegisterConflictException("Email does not exist");
        }

        return emailRequestDTO;
    }


}
