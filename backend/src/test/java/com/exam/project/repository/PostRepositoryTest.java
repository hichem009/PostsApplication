package com.exam.project.repository;

import com.exam.project.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    // ================= SAVE & FIND =================
    @Test
    void shouldSaveAndFindPost() {
        Post post = new Post("Spring", "Spring Boot Test", true);

        Post savedPost = postRepository.save(post);

        assertThat(savedPost.getId()).isGreaterThan(0);
    }

    // ================= FIND BY PUBLISHED =================
    @Test
    void shouldFindPublishedPosts() {
        postRepository.save(new Post("Post 1", "Desc 1", true));
        postRepository.save(new Post("Post 2", "Desc 2", false));

        List<Post> publishedPosts = postRepository.findByPublished(true);

        assertThat(publishedPosts).hasSize(1);
        assertThat(publishedPosts.get(0).isPublished()).isTrue();
    }

    // ================= FIND BY TITLE (IGNORE CASE) =================
    @Test
    void shouldFindByTitleContainingIgnoreCase() {
        postRepository.save(new Post("Spring Boot", "Desc", true));
        postRepository.save(new Post("Java Basics", "Desc", true));

        List<Post> result = postRepository.findByTitleContainingIgnoreCase("spring");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).containsIgnoringCase("spring");
    }

    // ================= DELETE =================
    @Test
    void shouldDeletePost() {
        Post post = postRepository.save(new Post("Delete Me", "Desc", false));

        postRepository.deleteById(post.getId());

        assertThat(postRepository.findById(post.getId())).isEmpty();
    }
}
