package com.infotec.springbootjwtroles.auth.role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "roles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String name; //ROLE USER / ROLE ADMIN
    private String description;
}
