package com.huertohogar.huerto_api.controller.categorias;

import com.huertohogar.huerto_api.model.categorias.Categoria;
import com.huertohogar.huerto_api.service.categorias.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
        "http://localhost:3000"
})
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    // GET /api/v1/categorias
    @GetMapping
    public List<Categoria> listar() {
        return service.listar();
    }

    // GET /api/v1/categorias/{id}
    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    // GET /api/v1/categorias/slug/{slug}
    @GetMapping("/slug/{slug}")
    public Categoria buscarPorSlug(@PathVariable String slug) {
        return service.buscarPorSlug(slug);
    }

    @PostMapping
    public Categoria crear(@RequestBody Categoria c) {
        return service.crear(c);
    }

    @PutMapping("/{id}")
    public Categoria actualizar(@PathVariable Integer id, @RequestBody Categoria c) {
        return service.actualizar(id, c);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
