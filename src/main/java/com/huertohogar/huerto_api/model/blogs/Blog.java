package com.huertohogar.huerto_api.model.blogs;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Blog {

    private Long id;           // ID numérico BD
    private String blogId;     // ID lógico (blogModal1, blogExtra_...)
    private String imagen;
    private String alt;
    private String titulo;
    private String tituloModal;
    private String resumen;
    private String intro;
    private List<String> contenido = new ArrayList<>();
    private String outro;
}
