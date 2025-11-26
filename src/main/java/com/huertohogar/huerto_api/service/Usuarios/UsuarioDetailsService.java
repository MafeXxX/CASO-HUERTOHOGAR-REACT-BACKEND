package com.huertohogar.huerto_api.service.usuarios;

import com.huertohogar.huerto_api.model.usuarios.Usuario;
import com.huertohogar.huerto_api.repository.usuarios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String roleUpper = u.getRole().toUpperCase(); // admin, cliente, vendedor
        return new User(
                u.getUsername(),
                u.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + roleUpper))
        );
    }
}
