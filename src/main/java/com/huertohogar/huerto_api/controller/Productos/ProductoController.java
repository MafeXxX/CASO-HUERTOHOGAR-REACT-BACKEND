// src/main/java/com/huertohogar/huerto_api/controller/productos/ProductoController.java
package com.huertohogar.huerto_api.controller.productos;

import com.huertohogar.huerto_api.model.productos.Producto;
import com.huertohogar.huerto_api.service.productos.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
        "http://localhost:3000"
})
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    // GET http://localhost:8080/api/v1/productos
    @GetMapping
    public List<Producto> listar() {
        return service.listar();
    }

    // GET http://localhost:8080/api/v1/productos/{id}
    @GetMapping("/{id}")
    public Producto buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // GET http://localhost:8080/api/v1/productos/codigo/{codigo}
    @GetMapping("/codigo/{codigo}")
    public Producto buscarPorCodigo(@PathVariable String codigo) {
        return service.buscarPorCodigo(codigo);
    }

    @PostMapping
    public Producto crear(@RequestBody Producto p) {
        return service.crear(p);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto p) {
        return service.actualizar(id, p);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
