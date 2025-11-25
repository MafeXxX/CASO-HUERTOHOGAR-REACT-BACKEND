package com.huertohogar.huerto_api.model.productos;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "PRODUCTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "descripcion_corta", length = 255)
    private String descripcionCorta;

    @Column(name = "descripcion_larga", length = 4000)
    private String descripcionLarga;

    @Column(length = 500)
    private String imagen;

    @Column(length = 100)
    private String categoria;

    @Column(length = 100)
    private String origen;

    @Column(name = "practicas_sostenibles", length = 500)
    private String practicasSostenibles;

    @ElementCollection
    @CollectionTable(
            name = "PRODUCTO_RECETA",
            joinColumns = @JoinColumn(name = "producto_id")
    )
    @Column(name = "receta", length = 200)
    private List<String> recetas;
}
