package com.huertohogar.huerto_api.controller.contacto;

import com.huertohogar.huerto_api.model.contacto.MensajeContacto;
import com.huertohogar.huerto_api.service.contacto.MensajeContactoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacto")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ContactoController {

    private final MensajeContactoService mensajeContactoService;

    @PostMapping
    public ResponseEntity<MensajeContacto> crear(@RequestBody MensajeContacto body) {
        MensajeContacto creado = mensajeContactoService.crear(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public List<MensajeContacto> listar() {
        return mensajeContactoService.listar();
    }
}
