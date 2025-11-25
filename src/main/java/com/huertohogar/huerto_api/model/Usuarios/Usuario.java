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
    private Long id;   // PK real en BD (no confundir con el "id" del JSON)

    @Column(nullable = false, unique = true, length = 50)
    private String username;   // admin, Maria, Pedro...

    @Column(nullable = false, length = 255)
    private String password;   // luego lo encriptaremos con Spring Security

    @Column(nullable = false, length = 20)
    private String role;       // admin, cliente, vendedor

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellido;

    @Column(length = 20)
    private String run;

    @Column(name = "fecha_nacimiento", length = 20)
    private String fechaNacimiento;  // lo dejamos como String (formato dd/MM/yyyy)

    @Column(length = 100)
    private String region;

    @Column(length = 100)
    private String comuna;

    @Column(length = 200)
    private String direccion;

    @Column(nullable = false, unique = true, length = 150)
    private String email;
}
