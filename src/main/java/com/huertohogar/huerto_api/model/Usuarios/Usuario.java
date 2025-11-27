package com.huertohogar.huerto_api.model.usuarios;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String role;   // admin, cliente, vendedor

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellido;

    @Column(length = 20)
    private String run;

    @Column(name = "fecha_nacimiento", length = 20)
    private String fechaNacimiento;

    @Column(length = 100)
    private String region;

    @Column(length = 100)
    private String comuna;

    @Column(length = 200)
    private String direccion;

    @Column(nullable = false, unique = true, length = 150)
    private String email;
}
