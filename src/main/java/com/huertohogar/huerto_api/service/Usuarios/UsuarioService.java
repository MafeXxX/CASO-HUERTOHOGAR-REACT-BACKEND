package com.huertohogar.huerto_api.service.usuarios;

import com.huertohogar.huerto_api.model.Usuarios.Usuario;

import java.util.List;

public interface UsuarioService {

    List<Usuario> listar();

    Usuario buscarPorId(Long id);

    Usuario crear(Usuario u);

    Usuario actualizar(Long id, Usuario u);

    void eliminar(Long id);
}
