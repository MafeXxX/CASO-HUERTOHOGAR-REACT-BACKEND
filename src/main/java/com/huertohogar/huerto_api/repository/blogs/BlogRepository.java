package com.huertohogar.huerto_api.repository.blogs;

import com.huertohogar.huerto_api.model.blogs.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepository {

    List<Blog> findAll();

    Optional<Blog> findById(Long id);

    Blog save(Blog blog);   // crea o actualiza según si tiene id

    void deleteById(Long id);
}
