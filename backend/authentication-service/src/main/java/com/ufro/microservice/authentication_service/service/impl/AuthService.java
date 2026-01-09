package com.ufro.microservice.authentication_service.service.impl;

import com.ufro.microservice.authentication_service.dto.*;
import com.ufro.microservice.authentication_service.exceptions.RegisterConflictException;
import com.ufro.microservice.authentication_service.mapper.IUserMapper;
import com.ufro.microservice.authentication_service.repository.IUserCrendentialRepository;
import com.ufro.microservice.authentication_service.service.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final IUserCrendentialRepository userCrendentialRepository;
    private final IUserMapper userMapper;


    public AuthService(IUserCrendentialRepository userCrendentialRepository, IUserMapper userMapper) {
        this.userCrendentialRepository = userCrendentialRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserCrendentialDTO registerUser(UserCrendentialDTO userCrendentialDTO) {
        if (userCrendentialRepository.existsByEmail(userCrendentialDTO.getEmail())) {
            throw new RegisterConflictException("Email already exists");
        }
        log.info("Registering user with email: {}", userCrendentialDTO.getEmail());
        return userMapper.registerToDTO(userCrendentialRepository.save(userMapper.registerToModel(userCrendentialDTO)));
    }

    @Override
    public LoginResponseDTO loginUser(String userLoginDTO) {
        LoginResponseDTO user = userMapper.toLoginDTO(userCrendentialRepository.findByEmail(userLoginDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en BD local")));
        log.info("User logged in with email: {}", userLoginDTO);
        return user;
    }

}
