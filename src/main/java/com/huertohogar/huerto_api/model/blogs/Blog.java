package com.huertohogar.huerto_api.model.blogs;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BLOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BLOG_ID", length = 50, nullable = false)
    private String blogId;

    @Column(length = 200)
    private String imagen;

    @Column(length = 150)
    private String alt;

    @Column(length = 150, nullable = false)
    private String titulo;

    @Column(name = "TITULO_MODAL", length = 200)
    private String tituloModal;

    @Column(name = "RESUMEN", length = 500, nullable = false)
    private String resumen;

    @Column(length = 2000)
    private String intro;

    // 👇 Campo simple, YA NO hay tabla BLOG_CONTENIDO
    @Column(name = "CONTENIDO", length = 4000)
    private String contenido;

    @Column(length = 2000)
    private String outro;
}
