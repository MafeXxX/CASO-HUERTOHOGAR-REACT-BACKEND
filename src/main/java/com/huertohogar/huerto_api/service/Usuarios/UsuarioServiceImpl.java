package com.huertohogar.huerto_api.service.usuarios;

import com.huertohogar.huerto_api.model.usuarios.Usuario;
import com.huertohogar.huerto_api.repository.usuarios.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioServiceImpl(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Usuario> listar() {
        return repo.findAll();
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Usuario crear(Usuario u) {
        // aquí podrías validar que username/email no estén repetidos
        return repo.save(u);
    }

    @Override
    public Usuario actualizar(Long id, Usuario u) {
        Usuario base = repo.findById(id).orElse(null);
        if (base == null) return null;

        base.setUsername(u.getUsername());
        base.setPassword(u.getPassword());
        base.setRole(u.getRole());
        base.setNombre(u.getNombre());
        base.setApellido(u.getApellido());
        base.setRun(u.getRun());
        base.setFechaNacimiento(u.getFechaNacimiento());
        base.setRegion(u.getRegion());
        base.setComuna(u.getComuna());
        base.setDireccion(u.getDireccion());
        base.setEmail(u.getEmail());

        return repo.save(base);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
