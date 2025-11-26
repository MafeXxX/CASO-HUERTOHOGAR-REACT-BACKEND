// src/main/java/com/huertohogar/huerto_api/controller/Usuarios/AuthController.java
package com.huertohogar.huerto_api.controller.usuarios;

import com.huertohogar.huerto_api.dto.usuarios.LoginRequestDTO;
import com.huertohogar.huerto_api.dto.usuarios.LoginResponseDTO;
import com.huertohogar.huerto_api.model.usuarios.Usuario;
import com.huertohogar.huerto_api.repository.usuarios.UsuarioRepository;
import com.huertohogar.huerto_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {

        // 1) Autenticar usuario (username + password)
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        // 2) Buscar usuario completo para obtener id y role
        Usuario u = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3) Generar token JWT
        String token = jwtUtil.generateToken(userDetails, u.getRole());

        // 4) Armar respuesta sin contraseña
        LoginResponseDTO response = LoginResponseDTO.builder()
                .id(u.getId())
                .username(u.getUsername())
                .role(u.getRole())
                .token(token)
                .build();

        return ResponseEntity.ok(response);
    }
}
