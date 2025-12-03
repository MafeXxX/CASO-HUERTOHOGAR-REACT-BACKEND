package com.huertohogar.huerto_api.controller.pedidos;

import com.huertohogar.huerto_api.service.pedidos.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/compras")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearCompra(
            @RequestBody Map<String, Object> payload
    ) {
        Map<String, Object> creada = compraService.registrarCompra(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public List<Map<String, Object>> listarCompras() {
        return compraService.listarCompras();
    }

    @GetMapping("/{id}")
    public Map<String, Object> obtenerCompra(@PathVariable Long id) {
        return compraService.obtenerCompra(id);
    }
}
