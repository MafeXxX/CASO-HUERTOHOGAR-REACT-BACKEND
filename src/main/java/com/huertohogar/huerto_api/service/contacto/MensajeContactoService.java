package com.huertohogar.huerto_api.service.contacto;

import com.huertohogar.huerto_api.model.contacto.MensajeContacto;

import java.util.List;

public interface MensajeContactoService {

    MensajeContacto crear(MensajeContacto mensaje);

    List<MensajeContacto> listar();
}
