// src/main/java/com/huertohogar/huerto_api/dto/Usuarios/UsuarioDTO.java
package com.huertohogar.huerto_api.dto.usuarios;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String username;
    private String role;
    private String nombre;
    private String apellido;
    private String run;
    private String fechaNacimiento;
    private String region;
    private String comuna;
    private String direccion;
    private String email;
}
