package com.project.health_gate.repository;

import com.project.health_gate.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("select p from Post p where p.id=:id")
    Post findOneById(@Param("id") Long id);
}
