package com.huertohogar.huerto_api.model.pedidos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMPRA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "DATA")
    private String data; // aquí guardamos el JSON completo de la compra

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;
}
