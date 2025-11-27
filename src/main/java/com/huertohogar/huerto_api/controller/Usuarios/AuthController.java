package com.huertohogar.huerto_api.controller.usuarios;

import com.huertohogar.huerto_api.dto.usuarios.LoginRequestDTO;
import com.huertohogar.huerto_api.dto.usuarios.LoginResponseDTO;
import com.huertohogar.huerto_api.dto.usuarios.UsuarioDTO;
import com.huertohogar.huerto_api.model.usuarios.Usuario;
import com.huertohogar.huerto_api.repository.usuarios.UsuarioRepository;
import com.huertohogar.huerto_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;   // 👈 inyectamos el encoder

    // =========================================================
    //                       LOGIN
    // =========================================================
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {

        String usernameOrEmail = request.getUsernameOrEmail();
        String password = request.getPassword();

        if (usernameOrEmail == null || usernameOrEmail.isBlank()
                || password == null || password.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Usuario> optUsuario =
                usuarioRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(
                        usernameOrEmail.trim(),
                        usernameOrEmail.trim()
                );

        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario usuario = optUsuario.get();

        // ✅ Comparamos usando BCrypt
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRole());

        UsuarioDTO usuarioDTO = mapToDTO(usuario);
        LoginResponseDTO response = new LoginResponseDTO(token, usuarioDTO);

        return ResponseEntity.ok(response);
    }

    // =========================================================
    //                      REGISTRO
    // =========================================================
    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> register(@RequestBody UsuarioDTO dto) {
        try {
            // Validación básica
            if (dto.getUsername() == null || dto.getUsername().isBlank()
                    || dto.getEmail() == null || dto.getEmail().isBlank()
                    || dto.getPassword() == null || dto.getPassword().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            boolean exists = usuarioRepository
                    .existsByUsernameIgnoreCaseOrEmailIgnoreCase(
                            dto.getUsername().trim(),
                            dto.getEmail().trim()
                    );

            if (exists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            Usuario usuario = mapToEntity(dto);

            // Rol por defecto
            if (usuario.getRole() == null || usuario.getRole().isBlank()) {
                usuario.setRole("cliente");
            }

            // 🔐 Hash de la contraseña
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

            Usuario saved = usuarioRepository.save(usuario);
            UsuarioDTO savedDTO = mapToDTO(saved);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
        } catch (Exception e) {
            e.printStackTrace(); // para que lo veas en la consola de NetBeans
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // =========================================================
    //                       MAPPERS
    // =========================================================
    private UsuarioDTO mapToDTO(Usuario u) {
        if (u == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setEmail(u.getEmail());
        dto.setRole(u.getRole());
        dto.setNombre(u.getNombre());
        dto.setApellido(u.getApellido());
        dto.setRun(u.getRun());
        dto.setFechaNacimiento(u.getFechaNacimiento());
        dto.setRegion(u.getRegion());
        dto.setComuna(u.getComuna());
        dto.setDireccion(u.getDireccion());
        // ⚠ NO devolvemos la contraseña
        return dto;
    }

    private Usuario mapToEntity(UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setId(dto.getId());
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setRole(dto.getRole());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setRun(dto.getRun());
        u.setFechaNacimiento(dto.getFechaNacimiento());
        u.setRegion(dto.getRegion());
        u.setComuna(dto.getComuna());
        u.setDireccion(dto.getDireccion());
        // La contraseña la seteamos en el register con el encoder
        return u;
    }
}
