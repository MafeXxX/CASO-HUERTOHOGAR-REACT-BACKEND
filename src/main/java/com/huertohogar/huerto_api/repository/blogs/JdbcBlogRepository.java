package com.huertohogar.huerto_api.repository.blogs;

import com.huertohogar.huerto_api.model.blogs.Blog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBlogRepository implements BlogRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBlogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // -------------------------------------------
    // MAPPER FILA → BLOG
    // -------------------------------------------
    private RowMapper<Blog> blogRowMapper() {
        return new RowMapper<>() {
            @Override
            public Blog mapRow(ResultSet rs, int rowNum) throws SQLException {
                Blog b = new Blog();
                b.setId(rs.getLong("ID"));
                b.setBlogId(rs.getString("BLOG_ID"));
                b.setImagen(rs.getString("IMAGEN"));
                b.setAlt(rs.getString("ALT"));
                b.setTitulo(rs.getString("TITULO"));
                b.setTituloModal(rs.getString("TITULO_MODAL"));
                b.setResumen(rs.getString("RESUMEN"));
                b.setIntro(rs.getString("INTRO"));

                String contenidoRaw = rs.getString("CONTENIDO");
                List<String> contenido = new ArrayList<>();
                if (contenidoRaw != null && !contenidoRaw.isBlank()) {
                    Arrays.stream(contenidoRaw.split("\n"))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .forEach(contenido::add);
                }
                b.setContenido(contenido);

                b.setOutro(rs.getString("OUTRO"));
                return b;
            }
        };
    }

    // -------------------------------------------
    // CRUD
    // -------------------------------------------

    @Override
    public List<Blog> findAll() {
        String sql = """
                SELECT ID, BLOG_ID, IMAGEN, ALT, TITULO, TITULO_MODAL,
                       RESUMEN, INTRO, CONTENIDO, OUTRO
                FROM BLOG
                ORDER BY ID
                """;
        return jdbcTemplate.query(sql, blogRowMapper());
    }

    @Override
    public Optional<Blog> findById(Long id) {
        String sql = """
                SELECT ID, BLOG_ID, IMAGEN, ALT, TITULO, TITULO_MODAL,
                       RESUMEN, INTRO, CONTENIDO, OUTRO
                FROM BLOG
                WHERE ID = ?
                """;
        List<Blog> list = jdbcTemplate.query(sql, blogRowMapper(), id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public Blog save(Blog blog) {
        // Contenido como texto (guardamos las líneas separadas por \n)
        String contenidoStr = "";
        if (blog.getContenido() != null && !blog.getContenido().isEmpty()) {
            contenidoStr = String.join("\n", blog.getContenido());
        }

        if (blog.getId() == null) {
            // INSERT → generamos ID simple con MAX(ID)+1
            Long nextId = jdbcTemplate.queryForObject(
                    "SELECT NVL(MAX(ID),0) + 1 FROM BLOG",
                    Long.class
            );
            blog.setId(nextId);

            String sql = """
                    INSERT INTO BLOG (
                      ID, BLOG_ID, IMAGEN, ALT, TITULO, TITULO_MODAL,
                      RESUMEN, INTRO, CONTENIDO, OUTRO
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;

            jdbcTemplate.update(sql,
                    blog.getId(),
                    blog.getBlogId(),
                    blog.getImagen(),
                    blog.getAlt(),
                    blog.getTitulo(),
                    blog.getTituloModal(),
                    blog.getResumen(),
                    blog.getIntro(),
                    contenidoStr,
                    blog.getOutro()
            );
        } else {
            // UPDATE
            String sql = """
                    UPDATE BLOG SET
                      BLOG_ID = ?,
                      IMAGEN = ?,
                      ALT = ?,
                      TITULO = ?,
                      TITULO_MODAL = ?,
                      RESUMEN = ?,
                      INTRO = ?,
                      CONTENIDO = ?,
                      OUTRO = ?
                    WHERE ID = ?
                    """;
            jdbcTemplate.update(sql,
                    blog.getBlogId(),
                    blog.getImagen(),
                    blog.getAlt(),
                    blog.getTitulo(),
                    blog.getTituloModal(),
                    blog.getResumen(),
                    blog.getIntro(),
                    contenidoStr,
                    blog.getOutro(),
                    blog.getId()
            );
        }
        return blog;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM BLOG WHERE ID = ?", id);
    }
}
