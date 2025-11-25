// src/main/java/com/huertohogar/huerto_api/config/SecurityConfig.java
package com.huertohogar.huerto_api.config;

import com.huertohogar.huerto_api.security.JwtFilter;
import com.huertohogar.huerto_api.service.Usuarios.UsuarioDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // login público
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // productos GET públicos (catálogo)
                        .requestMatchers("/api/v1/productos/**").permitAll()
                        // TODO: ajustar para rutas de admin/vendedor/cliente
                        .anyRequest().authenticated()
                )
                .userDetailsService(usuarioDetailsService);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Para que funcionen tus contraseñas tal como están en JSON
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        // Para producción -> return new BCryptPasswordEncoder();
    }
}
