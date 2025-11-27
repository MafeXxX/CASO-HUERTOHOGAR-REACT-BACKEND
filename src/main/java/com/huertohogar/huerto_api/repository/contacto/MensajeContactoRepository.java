package com.huertohogar.huerto_api.repository.contacto;

import com.huertohogar.huerto_api.model.contacto.MensajeContacto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeContactoRepository extends JpaRepository<MensajeContacto, Long> {

    // Últimos primero
    List<MensajeContacto> findAllByOrderByFechaDesc();
}
