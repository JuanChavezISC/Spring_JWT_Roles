package com.infotec.springbootjwtroles.auth.service;

import com.infotec.springbootjwtroles.auth.dto.RequestsResponses;
import com.infotec.springbootjwtroles.auth.role.Role;
import com.infotec.springbootjwtroles.auth.role.RoleRepository;
import com.infotec.springbootjwtroles.auth.user.UserAccount;
import com.infotec.springbootjwtroles.auth.user.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service @RequiredArgsConstructor
public class UserAccountService {
    private final UserAccountRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public UserAccount register(RequestsResponses.RegisterRequest dto){
        userRepo.findByUsername(dto.username()).ifPresent(u -> {
            throw new IllegalArgumentException("Username en uso");
        });
        userRepo.findByEmail(dto.email()).ifPresent(u -> {
            throw new IllegalArgumentException("Email en uso");
        });

        // Asegurar que exista ROLE_USER
        Role userRole = roleRepo.findByName("ROLE_USER")
                .orElseGet(() -> roleRepo.save(Role.builder()
                                .name("ROLE_USER")
                        .description("Usuario basico")
                        .build()));

        UserAccount user = UserAccount.builder()
                .username(dto.username())
                .email(dto.email())
                .passwordHash(encoder.encode(dto.password()))
                .roles(Set.of(userRole))
                .build();
        return userRepo.save(user);
    }
}
