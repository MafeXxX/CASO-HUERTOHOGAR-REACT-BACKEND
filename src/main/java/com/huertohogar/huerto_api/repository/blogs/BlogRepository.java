package com.huertohogar.huerto_api.repository.blogs;

import com.huertohogar.huerto_api.model.blogs.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findByBlogId(String blogId);
}
