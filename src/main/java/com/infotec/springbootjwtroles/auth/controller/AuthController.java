package com.infotec.springbootjwtroles.auth.controller;

import com.infotec.springbootjwtroles.auth.dto.RequestsResponses;
import com.infotec.springbootjwtroles.auth.service.AuthService;
import com.infotec.springbootjwtroles.auth.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor
public class AuthController {
    private final UserAccountService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RequestsResponses.RegisterRequest
                                              request) {
        var user = userService.register(request);
        return ResponseEntity.ok(Map.of("message",
                "Usuario registrado", "username", user.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<RequestsResponses.AuthResponse> login(@Valid @RequestBody RequestsResponses.LoginRequest req){
        return ResponseEntity.ok(authService.login(req));
    }
}
