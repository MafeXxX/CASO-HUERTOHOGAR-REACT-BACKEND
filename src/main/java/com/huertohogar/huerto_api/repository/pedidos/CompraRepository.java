package com.huertohogar.huerto_api.repository.pedidos;

import com.huertohogar.huerto_api.model.pedidos.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findAllByOrderByFechaCreacionDesc();
}
