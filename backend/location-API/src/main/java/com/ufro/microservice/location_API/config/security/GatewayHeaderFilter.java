package com.ufro.microservice.location_API.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class GatewayHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Leer el UID que insertó el Gateway
        String uid = request.getHeader("X-User-Uid");

        // 2. Si existe, lo metemos al contexto de seguridad como un usuario autenticado
        if (uid != null && !uid.isEmpty()) {
            // Creamos un token simple.
            // Principal = uid (String)
            // Credentials = null
            // Authorities = empty (o podrías pasar roles por header también)
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(uid, null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}