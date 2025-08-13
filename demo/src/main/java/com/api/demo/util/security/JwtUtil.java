package com.api.demo.util.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtUtil {
    private static final Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final JwtProperties jwtProperties;
    private static final long EXPIRATION_MS = 1000 * 60 * 60; // 1 hora
    private final String val = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1NDMyMzg0MywiZXhwIjoxNzU0MzI3NDQzfQ";



    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(jwtProperties.getSigningKey())
                .compact();
    }
    public  String validateAndExtractUsername(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSigningKey())
                .build()
                .parseClaimsJws(token);
        return claims.getBody().getSubject();
    }
}