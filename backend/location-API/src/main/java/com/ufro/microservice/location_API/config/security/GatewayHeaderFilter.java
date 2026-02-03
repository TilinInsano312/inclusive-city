package com.ufro.microservice.location_API.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class GatewayHeaderFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(GatewayHeaderFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Log para ver si el filtro se ejecuta
        String path = request.getRequestURI();
        log.info(">>> GatewayHeaderFilter ejecutándose para: {}", path);

        // 2. Leer el UID
        String uid = request.getHeader("X-User-Uid");
        log.info(">>> Header X-User-Uid recibido: '{}'", uid);

        if (uid != null && !uid.isEmpty()) {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(uid, null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info(">>> Autenticación establecida exitosamente para: {}", uid);
        } else {
            log.warn(">>> NO se encontró header X-User-Uid. El usuario quedará como Anónimo.");
        }

        filterChain.doFilter(request, response);
    }
}