package com.ufro.microservice.authentication_service.service.impl;

import com.ufro.microservice.authentication_service.repository.IUserCrendentialRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserCrendentialRepository userCrendentialRepository;

    public CustomUserDetailsService(IUserCrendentialRepository userCrendentialRepository) {
        this.userCrendentialRepository = userCrendentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userCrendentialRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }
}
