package com.huertohogar.huerto_api.model.categorias;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CATEGORIA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @Column(name = "ID")
    private Integer id;   // usamos el mismo id que en tu JSON

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column(name = "SLUG", length = 100, nullable = false, unique = true)
    private String slug;
}
