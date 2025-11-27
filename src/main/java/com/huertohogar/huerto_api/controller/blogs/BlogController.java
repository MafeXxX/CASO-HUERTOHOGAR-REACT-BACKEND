package com.huertohogar.huerto_api.controller.blogs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huertohogar.huerto_api.model.blogs.Blog;
import com.huertohogar.huerto_api.service.blogs.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/v1/blogs")
@CrossOrigin(origins = "*")
public class BlogController {

    private final BlogService blogService;
    private final ObjectMapper objectMapper;

    public BlogController(BlogService blogService, ObjectMapper objectMapper) {
        this.blogService = blogService;
        this.objectMapper = objectMapper;
    }

    private Blog buildBlogFromBody(Map<String, Object> body) {
        Blog blog = new Blog();

        Object idObj = body.get("id");
        if (idObj instanceof Number n) blog.setId(n.longValue());

        String blogId = (String) body.get("blogId");
        if (blogId == null || blogId.isBlank()) {
            blogId = "blogExtra_" + System.currentTimeMillis();
        }
        blog.setBlogId(blogId);

        blog.setTitulo((String) body.getOrDefault("titulo", ""));
        blog.setResumen((String) body.getOrDefault("resumen", ""));
        blog.setImagen((String) body.getOrDefault("imagen", ""));
        blog.setAlt((String) body.getOrDefault("alt", ""));
        blog.setTituloModal((String) body.getOrDefault("tituloModal", ""));
        blog.setIntro((String) body.getOrDefault("intro", ""));
        blog.setOutro((String) body.getOrDefault("outro", ""));

        Object contenidoObj = body.get("contenido");
        List<String> contenido = new ArrayList<>();

        if (contenidoObj instanceof List<?> list) {
            for (Object o : list) {
                if (o != null) {
                    String s = o.toString().trim();
                    if (!s.isEmpty()) contenido.add(s);
                }
            }
        } else if (contenidoObj instanceof String s) {
            Arrays.stream(s.split("\n"))
                    .map(String::trim)
                    .filter(str -> !str.isEmpty())
                    .forEach(contenido::add);
        }

        blog.setContenido(contenido);
        return blog;
    }

    @GetMapping
    public List<Blog> listarBlogs() {
        return blogService.listarTodos();
    }

    @GetMapping("/{id}")
    public Blog obtenerBlog(@PathVariable Long id) {
        return blogService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<Blog> crearBlog(@RequestBody String body) {
        try {
            Map<String, Object> map = objectMapper.readValue(
                    body,
                    new TypeReference<Map<String, Object>>() {}
            );
            Blog blog = buildBlogFromBody(map);
            Blog creado = blogService.crear(blog);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "JSON inválido en creación de blog",
                    e
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> actualizarBlog(
            @PathVariable Long id,
            @RequestBody String body
    ) {
        try {
            Map<String, Object> map = objectMapper.readValue(
                    body,
                    new TypeReference<Map<String, Object>>() {}
            );
            Blog blog = buildBlogFromBody(map);
            Blog actualizado = blogService.actualizar(id, blog);
            return ResponseEntity.ok(actualizado);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "JSON inválido en actualización de blog",
                    e
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBlog(@PathVariable Long id) {
        blogService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
