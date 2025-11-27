package com.huertohogar.huerto_api.model.contacto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MENSAJE_CONTACTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "CORREO", nullable = false, length = 150)
    private String correo;

    @Column(name = "ASUNTO", nullable = false, length = 150)
    private String asunto;

    @Lob
    @Column(name = "MENSAJE", nullable = false)
    private String mensaje;

    // IMPORTANTE: el JSON que ve el front tendrá la propiedad "fecha"
    @Column(name = "FECHA_CREACION")
    private LocalDateTime fecha;

    @PrePersist
    public void prePersist() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
