package com.infotec.springbootjwtroles.auth.service;

import com.infotec.springbootjwtroles.auth.dto.RequestsResponses;
import com.infotec.springbootjwtroles.auth.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final UserDetailsService uds;
    private final JwtService jwtService;

    @Value("${app.security.jwt.expiration-minutes}")
    private long expMin;

    public RequestsResponses.AuthResponse login(RequestsResponses.LoginRequest req){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(),req.password()));
        var user = uds.loadUserByUsername(req.username());
        var roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
        String token = jwtService.generateToken(user.getUsername(), roles);
        return new RequestsResponses.AuthResponse(token, "Bearer", expMin * 60);
    }
}
