package com.infotec.springbootjwtroles.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RequestsResponses {
    public record RegisterRequest(
      @NotBlank String username,
      @NotBlank @Email String email,
      @NotBlank String password
    ){}

    public record LoginRequest(
            @NotBlank String username,
            @NotBlank String password
    ){}

    public record AuthResponse(
            String token,
            String tokenType,
            long expiresInSeconds
    ) {}
}
