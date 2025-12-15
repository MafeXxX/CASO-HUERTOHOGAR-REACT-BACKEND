package com.huertohogar.huerto_api.model.pedidos;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsuarioCompraId implements Serializable {
    private Long usuarioId;
    private Long compraId;
}
