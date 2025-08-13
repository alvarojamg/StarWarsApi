package com.api.demo.controller;

import com.api.demo.config.SecurityConfig;
import com.api.demo.util.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    @PostMapping("/token")
    public ResponseEntity<?> token(@RequestBody Map<String, String> cred) {
        String user = cred.get("username");
        String pass = cred.get("password");

        if (SecurityConfig.validateUser(user, pass)) {
            String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "invalid cretentials"));
        }
    }
}