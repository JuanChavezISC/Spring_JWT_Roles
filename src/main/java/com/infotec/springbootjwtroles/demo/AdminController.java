package com.infotec.springbootjwtroles.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController @RequestMapping("/api/admin")
public class AdminController {
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

}
