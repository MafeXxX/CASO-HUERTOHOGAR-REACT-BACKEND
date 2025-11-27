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

    @Column(nullable = false, length = 10, unique = true)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Integer precio;   // mapea NUMBER(10,2)

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "descripcion_corta", length = 255)
    private String descripcionCorta;

    @Column(name = "descripcion_larga", length = 2000)
    private String descripcionLarga;

    @Column(length = 255)
    private String imagen;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(length = 100)
    private String origen;

    @Column(name = "practicas_sostenibles", length = 255)
    private String practicasSostenibles;

    // 👇 AQUÍ EL CAMBIO IMPORTANTE: EAGER
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "PRODUCTO_RECETA",
            joinColumns = @JoinColumn(name = "producto_id")
    )
    @Column(name = "receta", length = 255)
    private List<String> recetas;
}
