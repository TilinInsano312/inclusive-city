package com.ufro.microservice.authentication_service.config.jwt;

import com.ufro.microservice.authentication_service.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {
    private final Long expiration;
    private final SecretKey secretKey;
    private final SecretKey refreshKey;

    public JwtUtils(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.secret.refresh}") String refreshSecret,
            @Value("${jwt.time}") Long expiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
        this.expiration = expiration;
    }

    public String getEmailFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date(System.currentTimeMillis() + expiration)) // 30 minutes expiration
                .signWith(secretKey)
                .compact();
    }
    public String refreshToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(refreshKey)
                .compact();
    }


    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
