package com.huertohogar.huerto_api.service.blogs;

import com.huertohogar.huerto_api.model.blogs.Blog;

import java.util.List;

public interface BlogService {

    List<Blog> listarTodos();

    Blog obtenerPorId(Long id);

    Blog crear(Blog blog);

    Blog actualizar(Long id, Blog blog);

    void eliminar(Long id);
}
