package com.huertohogar.huerto_api.service.pedidos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huertohogar.huerto_api.model.pedidos.Compra;
import com.huertohogar.huerto_api.model.productos.Producto;
import com.huertohogar.huerto_api.repository.pedidos.CompraRepository;
import com.huertohogar.huerto_api.repository.productos.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final ProductoRepository productoRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static int toInt(Object v, int fb) {
        try {
            if (v == null) return fb;
            return Integer.parseInt(String.valueOf(v));
        } catch (Exception e) {
            return fb;
        }
    }

    @Override
    @Transactional
    public Map<String, Object> registrarCompra(Map<String, Object> payload) {
        try {
            if (payload == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload vacío");
            }

            if (!payload.containsKey("cliente") || !payload.containsKey("productos")) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El payload debe incluir 'cliente' y 'productos'"
                );
            }

            Object productosObj = payload.get("productos");
            if (!(productosObj instanceof List<?> productosList)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "productos debe ser una lista");
            }

            // ✅ 1) Descontar stock en DB
            for (Object itemObj : productosList) {
                if (!(itemObj instanceof Map<?, ?> item)) continue;

                String codigo = item.get("codigo") != null ? String.valueOf(item.get("codigo")) : null;
                int cantidad = toInt(item.get("cantidad"), 1);

                if (codigo == null || codigo.isBlank()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cada producto debe incluir 'codigo'");
                }
                if (cantidad <= 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cantidad inválida para " + codigo);
                }

                Producto prod = productoRepository.findByCodigo(codigo)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Producto no encontrado: " + codigo
                        ));

                int stockActual = (prod.getStock() != null) ? prod.getStock() : 0;

                if (stockActual < cantidad) {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Stock insuficiente para " + codigo +
                                    " (stock=" + stockActual + ", solicitado=" + cantidad + ")"
                    );
                }

                prod.setStock(stockActual - cantidad);
                productoRepository.save(prod);
            }

            // ✅ 2) Guardar compra (snapshot)
            payload.putIfAbsent("fecha", LocalDateTime.now().toString());

            String json = objectMapper.writeValueAsString(payload);

            Compra compra = Compra.builder()
                    .data(json)
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            Compra guardada = compraRepository.save(compra);

            Map<String, Object> respuesta = new LinkedHashMap<>(payload);
            respuesta.put("id", guardada.getId());
            return respuesta;

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al registrar la compra",
                    e
            );
        }
    }

    @Override
    public List<Map<String, Object>> listarCompras() {
        try {
            return compraRepository.findAllByOrderByFechaCreacionDesc()
                    .stream()
                    .map(this::toMap)
                    .toList();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al listar las compras",
                    e
            );
        }
    }

    @Override
    public Map<String, Object> obtenerCompra(Long id) {
        try {
            Compra compra = compraRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Compra no encontrada"
                    ));
            return toMap(compra);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al obtener la compra",
                    e
            );
        }
    }

    private Map<String, Object> toMap(Compra compra) {
        try {
            Map<String, Object> map = objectMapper.readValue(
                    compra.getData(),
                    new TypeReference<Map<String, Object>>() {}
            );
            map.put("id", compra.getId());
            map.putIfAbsent("fechaCreacion", compra.getFechaCreacion());
            return map;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al transformar la compra",
                    e
            );
        }
    }
}
