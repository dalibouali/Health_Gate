package com.project.health_gate.repository;

import com.project.health_gate.entities.Comment;
import com.project.health_gate.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("select c from Comment c where c.id=:id")
    Comment findOneById(@Param("id") Long id);
}
