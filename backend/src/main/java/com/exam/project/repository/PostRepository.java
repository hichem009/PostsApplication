package com.exam.project.repository;

import java.util.List;

import com.exam.project.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findByPublished(boolean published);

  List<Post> findByTitleContainingIgnoreCase(String title);
}
