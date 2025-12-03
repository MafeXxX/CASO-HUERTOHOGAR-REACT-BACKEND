package com.huertohogar.huerto_api.service.blogs;

import com.huertohogar.huerto_api.model.blogs.Blog;
import com.huertohogar.huerto_api.repository.blogs.BlogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<Blog> listarTodos() {
        return blogRepository.findAll();
    }

    @Override
    public Blog obtenerPorId(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Blog no encontrado"
                ));
    }

    @Override
    public Blog crear(Blog blog) {
        blog.setId(null); // aseguramos INSERT
        return blogRepository.save(blog);
    }

    @Override
    public Blog actualizar(Long id, Blog blog) {
        Blog existente = obtenerPorId(id); // lanza 404 si no existe
        blog.setId(existente.getId());
        // si no viene blogId, usamos el anterior
        if (blog.getBlogId() == null || blog.getBlogId().isBlank()) {
            blog.setBlogId(existente.getBlogId());
        }
        return blogRepository.save(blog);
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id); // para lanzar 404 si no existe
        blogRepository.deleteById(id);
    }
}
