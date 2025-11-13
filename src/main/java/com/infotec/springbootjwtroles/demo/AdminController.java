package com.infotec.springbootjwtroles.demo;

import com.infotec.springbootjwtroles.auth.service.AdminUserService;
import com.infotec.springbootjwtroles.auth.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController @RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminUserService adminUserService;

    @GetMapping("/health")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String,String> health() {
        return Map.of("status","ADMIN OK");
    }

    //Simulación de acceso a información privada solo administradores
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> dashboard() {
        return Map.of(
                "status", "OK",
                "usuariosActivos", 25,
                "tareasPendientes", 3
        );
    }

    @PostMapping("/assign-role")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, String> assignRole(@RequestParam String username,@RequestParam String roleName){
        var updateUser = adminUserService.assignRole(username, roleName);

        return Map.of(
                "message", "Rol asignado exitosamente",
                "user", updateUser.getUsername(),
                "roles", updateUser.getRoles().toString()
        );
    }

}
