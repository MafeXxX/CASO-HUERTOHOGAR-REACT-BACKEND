package com.huertohogar.huerto_api.model.blogs;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(name = "blog_id", nullable = false, unique = true, length = 50)
    private String blogId;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String resumen;

    @Column(length = 200)
    private String imagen;

    @Column(length = 150)
    private String alt;

    @Column(name = "titulo_modal", length = 200)
    private String tituloModal;

    @Column(length = 2000)
    private String intro;

    @ElementCollection
    @CollectionTable(
            name = "BLOG_CONTENIDO",
            joinColumns = @JoinColumn(name = "blog_id_fk")
    )
    @Column(name = "parrafo", length = 1000)
    private List<String> contenido;

    @Column(length = 2000)
    private String outro;
}
