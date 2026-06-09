package com.godavari.user.controller;

import com.godavari.user.dto.*;
import com.godavari.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        log.info("==> REGISTER attempt for email: {}, role: {}", req.getEmail(), req.getRole());
        try {
            AuthResponse response = userService.register(req);
            log.info("==> REGISTER SUCCESS: {} registered as {}", req.getEmail(), response.getRole());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("==> REGISTER FAILED for {}: {}", req.getEmail(), e.getMessage());
            throw e;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        log.info("==> LOGIN attempt for email: {}", req.getEmail());
        try {
            AuthResponse response = userService.login(req);
            log.info("==> LOGIN SUCCESS: {} logged in as {}", req.getEmail(), response.getRole());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.warn("==> LOGIN FAILED for {}: {}", req.getEmail(), e.getMessage());
            throw e;
        }
    }
}
