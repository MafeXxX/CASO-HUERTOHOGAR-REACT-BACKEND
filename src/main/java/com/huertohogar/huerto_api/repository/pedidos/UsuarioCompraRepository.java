package com.huertohogar.huerto_api.repository.pedidos;

import com.huertohogar.huerto_api.model.pedidos.UsuarioCompra;
import com.huertohogar.huerto_api.model.pedidos.UsuarioCompraId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioCompraRepository extends JpaRepository<UsuarioCompra, UsuarioCompraId> {
}
