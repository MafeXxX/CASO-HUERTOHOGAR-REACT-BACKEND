package com.huertohogar.huerto_api.service.categorias;

import com.huertohogar.huerto_api.model.categorias.Categoria;
import com.huertohogar.huerto_api.repository.categorias.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
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

    // ==========================
    // Helpers
    // ==========================
    private String slugify(String nombre) {
        if (nombre == null) return null;

        String s = nombre.trim().toLowerCase();

        // Quitar tildes
        s = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Reemplazar todo lo que no sea [a-z0-9] por guiones
        s = s.replaceAll("[^a-z0-9]+", "-");

        // Quitar guiones duplicados o al inicio/fin
        s = s.replaceAll("(^-+|-+$)", "");
        return s;
    }

    @Override
    public Categoria crear(Categoria c) {
        // Normalizar slug en backend por seguridad
        if (c.getNombre() != null && (c.getSlug() == null || c.getSlug().isBlank())) {
            c.setSlug(slugify(c.getNombre()));
        }

        // Si viene null, lo dejamos null (permitido)
        // c.setDescripcion(c.getDescripcion());

        return repo.save(c);
    }

    @Override
    public Categoria actualizar(Integer id, Categoria c) {
        Categoria base = repo.findById(id).orElse(null);
        if (base == null) return null;

        base.setNombre(c.getNombre());

        // Si viene slug explícito, lo usamos; si no, lo regeneramos a partir del nombre
        if (c.getSlug() != null && !c.getSlug().isBlank()) {
            base.setSlug(c.getSlug());
        } else if (c.getNombre() != null) {
            base.setSlug(slugify(c.getNombre()));
        }

        // ✅ CLAVE: ahora sí actualiza la DESCRIPCION
        // Esto permite actualizarla y también permitir null (vaciarla) si lo mandas así desde frontend.
        base.setDescripcion(c.getDescripcion());

        return repo.save(base);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
