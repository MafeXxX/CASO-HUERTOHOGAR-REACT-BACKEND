// src/main/java/com/huertohogar/huerto_api/dto/Usuarios/LoginResponseDTO.java
package com.huertohogar.huerto_api.dto.usuarios;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private Long id;
    private String username;
    private String role;
    private String token;
}
