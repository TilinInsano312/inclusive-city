package com.ufro.microservice.authentication_service.service.impl;

import com.ufro.microservice.authentication_service.config.jwt.JwtUtils;
import com.ufro.microservice.authentication_service.dto.*;
import com.ufro.microservice.authentication_service.exceptions.RegisterConflictException;
import com.ufro.microservice.authentication_service.mapper.IUserMapper;
import com.ufro.microservice.authentication_service.model.User;
import com.ufro.microservice.authentication_service.repository.IUserCrendentialRepository;
import com.ufro.microservice.authentication_service.service.IAuthService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService implements IAuthService {

    private final IUserCrendentialRepository userCrendentialRepository;
    private final IUserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private StringRedisTemplate redisTemplate; // Cliente de Redis


    public AuthService(IUserCrendentialRepository userCrendentialRepository, IUserMapper userMapper, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EmailService emailService, StringRedisTemplate redisTemplate) {
        this.userCrendentialRepository = userCrendentialRepository;
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.redisTemplate = redisTemplate;
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
        String hashedPassword = passwordEncoder.encode(resetPasswordDTO.getNewPassword());
        redisTemplate.delete("OTP:" + resetPasswordDTO.getEmail());
        return userCrendentialRepository.updateUserByEmail(
                resetPasswordDTO.getEmail(),
                hashedPassword
        );
    }

    @Override
    public EmailDTO sendResetEmail(EmailDTO emailRequestDTO) { //Por terminar @BerAxz
        if (!(userCrendentialRepository.existsByEmail(emailRequestDTO.getEmail()))) {
            throw new RegisterConflictException("Email does not exist");
        }
        // 2. Generar Código
        String code = String.format("%06d", new Random().nextInt(999999));

        // 3. Guardar en Redis: Clave=Email, Valor=Código, Expiración=15 min
         redisTemplate.opsForValue().set("OTP:" + emailRequestDTO.getEmail(), code, 15, TimeUnit.MINUTES);

        // 4. Enviar correo
        emailService.sendEmail(emailRequestDTO.getEmail(), "Tu código", "Código: " + code);

        return emailRequestDTO;
    }
    public boolean verifyCode(VerifyCodeRequest verifyCodeRequest) {
        String storedCode = redisTemplate.opsForValue().get("OTP:" + verifyCodeRequest.getEmail());
        return storedCode != null && storedCode.equals(verifyCodeRequest.getCode());
    }


}
