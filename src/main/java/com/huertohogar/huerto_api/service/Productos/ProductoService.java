package com.huertohogar.huerto_api.service.productos;

import com.huertohogar.huerto_api.model.Productos.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> listar();

    Producto buscarPorId(Long id);

    Producto buscarPorCodigo(String codigo);

    Producto crear(Producto p);

    Producto actualizar(Long id, Producto p);

    void eliminar(Long id);
}
