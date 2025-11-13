package com.infotec.springbootjwtroles.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController @RequestMapping("/api/user")
public class UserController {
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Map<String,String> profile(Authentication auth) {
        return Map.of("message","Perfil de usuario", "username", auth.getName());
    }
}
