package com.huertohogar.huerto_api.service.pedidos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huertohogar.huerto_api.model.pedidos.Compra;
import com.huertohogar.huerto_api.repository.pedidos.CompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> registrarCompra(Map<String, Object> payload) {
        try {
            if (!payload.containsKey("cliente") || !payload.containsKey("productos")) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "El payload debe incluir 'cliente' y 'productos'"
                );
            }

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
