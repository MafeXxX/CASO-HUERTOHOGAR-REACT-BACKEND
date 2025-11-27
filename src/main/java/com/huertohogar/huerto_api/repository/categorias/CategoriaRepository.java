package com.huertohogar.huerto_api.repository.categorias;

import com.huertohogar.huerto_api.model.categorias.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Categoria findBySlug(String slug);

    Categoria findByNombre(String nombre);
}
