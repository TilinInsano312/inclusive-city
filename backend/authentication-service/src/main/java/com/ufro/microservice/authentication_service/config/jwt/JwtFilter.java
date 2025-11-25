package com.ufro.microservice.authentication_service.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);


    public JwtFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        log.info("Authorization header present: {}", authHeader != null);

        if (!processAuthentication(authHeader, response)) {
            return;
        }

        filterChain.doFilter(request, response);
    }


    private boolean processAuthentication(String authHeader, HttpServletResponse response) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return true;
        }

        String jwt = authHeader.substring(7);
        return validateAndSetAuthentication(jwt, response);
    }

    private boolean validateAndSetAuthentication(String jwt, HttpServletResponse response) {
        try {
            if (!jwtUtils.isTokenValid(jwt)) {
                handleInvalidToken(response, "Token JWT inválido o expirado");
                return false;
            }

            setAuthentication(jwt);
            return true;
        } catch (Exception e) {
            log.error("Error procesando JWT: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    private void setAuthentication(String jwt) {
        String email = jwtUtils.getEmailFromToken(jwt);
        UserDetails user = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleInvalidToken(HttpServletResponse response, String message) {
        log.warn(message);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
