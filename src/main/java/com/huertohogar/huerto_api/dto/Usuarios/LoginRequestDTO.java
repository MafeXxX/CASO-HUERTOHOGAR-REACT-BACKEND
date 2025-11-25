// src/main/java/com/huertohogar/huerto_api/dto/Usuarios/LoginRequestDTO.java
package com.huertohogar.huerto_api.dto.usuarios;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    private String username;
    private String password;
}
