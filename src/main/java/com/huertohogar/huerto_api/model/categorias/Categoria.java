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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_seq")
    @SequenceGenerator(
            name = "categoria_seq",
            sequenceName = "CATEGORIA_SEQ",
            allocationSize = 1
    )
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column(name = "SLUG", length = 100, nullable = false, unique = true)
    private String slug;

    @Column(name = "DESCRIPCION", length = 2000)
    private String descripcion;
}
