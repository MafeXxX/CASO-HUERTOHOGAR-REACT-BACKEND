package com.huertohogar.huerto_api.service.blogs;

import com.huertohogar.huerto_api.model.blogs.Blog;
import com.huertohogar.huerto_api.repository.blogs.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository repo;

    public BlogServiceImpl(BlogRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Blog> listar() {
        return repo.findAll();
    }

    @Override
    public Blog buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Blog buscarPorBlogId(String blogId) {
        return repo.findByBlogId(blogId).orElse(null);
    }

    @Override
    public Blog crear(Blog blog) {
        return repo.save(blog);
    }

    @Override
    public Blog actualizar(Long id, Blog blog) {
        Blog base = repo.findById(id).orElse(null);
        if (base == null) return null;

        base.setBlogId(blog.getBlogId());
        base.setTitulo(blog.getTitulo());
        base.setResumen(blog.getResumen());
        base.setImagen(blog.getImagen());
        base.setAlt(blog.getAlt());
        base.setTituloModal(blog.getTituloModal());
        base.setIntro(blog.getIntro());
        base.setContenido(blog.getContenido());
        base.setOutro(blog.getOutro());

        return repo.save(base);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
