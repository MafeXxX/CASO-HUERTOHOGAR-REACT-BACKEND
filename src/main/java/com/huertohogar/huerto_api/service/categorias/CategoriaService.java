package com.huertohogar.huerto_api.service.categorias;

import com.huertohogar.huerto_api.model.categorias.Categoria;

import java.util.List;

public interface CategoriaService {

    List<Categoria> listar();

    Categoria buscarPorId(Integer id);

    Categoria buscarPorSlug(String slug);

    Categoria crear(Categoria c);

    Categoria actualizar(Integer id, Categoria c);

    void eliminar(Integer id);
}
