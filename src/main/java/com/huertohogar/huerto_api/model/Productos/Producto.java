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

    // Código único del producto (ej: MANZ001)
    @Column(length = 10, nullable = false, unique = true)
    private String codigo;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "descripcion_corta", length = 255)
    private String descripcionCorta;

    @Column(name = "descripcion_larga", length = 2000)
    private String descripcionLarga;

    @Lob
    @Column
    private String imagen;

    // 🔢 ID numérico de categoría (columna CATEGORY_ID en la BDD)
    @Column(name = "CATEGORY_ID")
    private Integer categoryId;

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

    // 🟢 Campo virtual: NO existe en la tabla, pero sí en el JSON que ve el frontend
    @Transient
    private String categoria;

    /**
     * Devuelve el nombre de la categoría.
     * - Si ya viene seteado (p.ej. desde productos extra en localStorage), se respeta.
     * - Si no, se calcula a partir de categoryId.
     */
    public String getCategoria() {
        if (categoria != null && !categoria.isBlank()) {
            return categoria;
        }
        if (categoryId == null) {
            return null;
        }
        return switch (categoryId) {
            case 1 -> "Frutas Frescas";
            case 2 -> "Verduras Orgánicas";
            case 3 -> "Productos Orgánicos";
            case 4 -> "Productos Lácteos";
            default -> null;
        };
    }

    /**
     * Permite setearla manualmente (por ejemplo, si viene desde otro origen),
     * sin afectar la columna CATEGORY_ID.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
