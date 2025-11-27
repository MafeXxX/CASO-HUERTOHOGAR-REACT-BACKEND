package com.huertohogar.huerto_api.service.pedidos;

import java.util.List;
import java.util.Map;

public interface CompraService {

    Map<String, Object> registrarCompra(Map<String, Object> payload);

    List<Map<String, Object>> listarCompras();

    Map<String, Object> obtenerCompra(Long id);
}
