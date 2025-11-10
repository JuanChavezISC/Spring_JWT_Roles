package com.infotec.springbootjwtroles.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    @Value("${app.security.jwt.secret}")
    private String secret;
    @Value("${app.security.jwt.expiration-minutes}")
    private long expirationMinutes;

    private SecretKey key(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, String... roles){
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirationMinutes * 60)))
                .signWith(key())
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isValid(String token, String expectedUsername){
        var payload = Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();

        return expectedUsername.equals(payload.getSubject()) && payload.getExpiration().after(new Date());
    }
}
