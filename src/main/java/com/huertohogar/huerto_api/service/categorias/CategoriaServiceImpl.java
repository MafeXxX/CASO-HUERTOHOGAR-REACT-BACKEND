package com.huertohogar.huerto_api.service.categorias;

import com.huertohogar.huerto_api.model.categorias.Categoria;
import com.huertohogar.huerto_api.repository.categorias.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repo;

    @Override
    public List<Categoria> listar() {
        return repo.findAll();
    }

    @Override
    public Categoria buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Categoria buscarPorSlug(String slug) {
        return repo.findBySlug(slug);
    }

    @Override
    public Categoria crear(Categoria c) {
        return repo.save(c);
    }

    @Override
    public Categoria actualizar(Integer id, Categoria c) {
        Categoria base = repo.findById(id).orElse(null);
        if (base == null) return null;

        base.setNombre(c.getNombre());
        base.setSlug(c.getSlug());

        return repo.save(base);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
