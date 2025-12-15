package com.huertohogar.huerto_api.model.pedidos;

import com.huertohogar.huerto_api.model.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "USUARIO_COMPRA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCompra {

    @EmbeddedId
    private UsuarioCompraId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("compraId")
    @JoinColumn(name = "COMPRA_ID", nullable = false)
    private Compra compra;

    @Column(name = "FECHA_ASOCIACION")
    private LocalDateTime fechaAsociacion;
}
