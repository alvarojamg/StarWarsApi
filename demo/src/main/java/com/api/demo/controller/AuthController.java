package com.api.demo.controller;

import com.api.demo.config.SecurityConfig;
import com.api.demo.util.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/token")
    public ResponseEntity<?> token(@RequestBody Map<String, String> cred) {
        String user = cred.get("username");
        String pass = cred.get("password");

        if (SecurityConfig.validateUser(user, pass)) {
            String token = JwtUtil.generateToken(user);
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "invalid cretentials"));
        }
    }
}