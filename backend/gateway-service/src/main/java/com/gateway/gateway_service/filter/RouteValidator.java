package com.gateway.gateway_service.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints = List.of(
            "/inclusive/api/v1/account/auth/login",
            "/inclusive/api/v1/account/auth/register",
            "/inclusive/api/v1/account/email/reset-password",
            "/inclusive/api/v1/account/reset-password",
            "/eureka"
    );
    public Predicate<ServerHttpRequest> isSecured = path -> openApiEndpoints
            .stream()
            .noneMatch(uri -> path.getURI().getPath().contains(uri));
}
