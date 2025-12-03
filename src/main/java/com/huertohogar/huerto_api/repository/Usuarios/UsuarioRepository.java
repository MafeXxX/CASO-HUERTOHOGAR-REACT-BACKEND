package com.huertohogar.huerto_api.repository.usuarios;

import com.huertohogar.huerto_api.model.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Para login por username o email
    Optional<Usuario> findByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);

    // Para validar duplicados en registro
    boolean existsByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);

    // Para cargar usuario por username (UserDetailsService)
    Optional<Usuario> findByUsername(String username);

    // Si quieres que ignore mayúsculas/minúsculas:
    Optional<Usuario> findByUsernameIgnoreCase(String username);
}
