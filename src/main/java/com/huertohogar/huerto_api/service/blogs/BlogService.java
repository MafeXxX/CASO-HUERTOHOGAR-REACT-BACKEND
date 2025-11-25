package com.huertohogar.huerto_api.service.blogs;

import com.huertohogar.huerto_api.model.blogs.Blog;

import java.util.List;

public interface BlogService {

    List<Blog> listar();

    Blog buscarPorId(Long id);

    Blog buscarPorBlogId(String blogId);

    Blog crear(Blog blog);

    Blog actualizar(Long id, Blog blog);

    void eliminar(Long id);
}
