package com.huertohogar.huerto_api.controller.blogs;

import com.huertohogar.huerto_api.model.blogs.Blog;
import com.huertohogar.huerto_api.service.blogs.BlogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
@CrossOrigin("*")
public class BlogController {

    private final BlogService service;

    public BlogController(BlogService service) {
        this.service = service;
    }

    @GetMapping
    public List<Blog> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Blog obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/modal/{blogId}")
    public Blog obtenerPorBlogId(@PathVariable String blogId) {
        return service.buscarPorBlogId(blogId);
    }

    @PostMapping
    public Blog crear(@RequestBody Blog blog) {
        return service.crear(blog);
    }

    @PutMapping("/{id}")
    public Blog actualizar(@PathVariable Long id, @RequestBody Blog blog) {
        return service.actualizar(id, blog);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
