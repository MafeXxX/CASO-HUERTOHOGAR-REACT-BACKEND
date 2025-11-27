package com.huertohogar.huerto_api.service.contacto;

import com.huertohogar.huerto_api.model.contacto.MensajeContacto;
import com.huertohogar.huerto_api.repository.contacto.MensajeContactoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MensajeContactoServiceImpl implements MensajeContactoService {

    private final MensajeContactoRepository repo;

    @Override
    public MensajeContacto crear(MensajeContacto mensaje) {
        // Creamos un objeto nuevo para no confiar en id/fecha que vengan del front
        MensajeContacto nuevo = MensajeContacto.builder()
                .nombre(mensaje.getNombre())
                .correo(mensaje.getCorreo())
                .asunto(mensaje.getAsunto())
                .mensaje(mensaje.getMensaje())
                .build();
        return repo.save(nuevo);
    }

    @Override
    public List<MensajeContacto> listar() {
        return repo.findAllByOrderByFechaDesc();
    }
}
