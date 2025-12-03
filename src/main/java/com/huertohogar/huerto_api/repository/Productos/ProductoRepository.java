package com.huertohogar.huerto_api.repository.productos;

import com.huertohogar.huerto_api.model.productos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar un producto por su código único
    Producto findByCodigo(String codigo);
}
