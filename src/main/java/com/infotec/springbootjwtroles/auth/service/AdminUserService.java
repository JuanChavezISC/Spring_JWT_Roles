package com.infotec.springbootjwtroles.auth.service;

import com.infotec.springbootjwtroles.auth.role.Role;
import com.infotec.springbootjwtroles.auth.role.RoleRepository;
import com.infotec.springbootjwtroles.auth.user.UserAccount;
import com.infotec.springbootjwtroles.auth.user.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class AdminUserService {

    private final UserAccountRepository userRepo;
    private final RoleRepository roleRepo;

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserAccount> listAll() {
        return userRepo.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserAccount assignRole(String username, String roleName) {
        String normalized = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;

        UserAccount user = userRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + username));

        Role role = roleRepo.findByName(normalized)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + normalized));

        user.getRoles().add(role);
        return userRepo.save(user);
    }

}
