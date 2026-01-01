package com.exam.project.service;

import com.exam.project.model.Post;
import com.exam.project.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post("Title", "Description", true);
    }

    // ================= GET ALL POSTS =================
    @Test
    void shouldReturnAllPosts() {
        when(postRepository.findAll()).thenReturn(List.of(post));

        ResponseEntity<List<Post>> response = postService.getAllPosts(null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        verify(postRepository).findAll();
    }

    @Test
    void shouldReturnNoContentWhenNoPosts() {
        when(postRepository.findAll()).thenReturn(List.of());

        ResponseEntity<List<Post>> response = postService.getAllPosts(null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(postRepository).findAll();
    }

    // ================= GET POST BY ID =================
    @Test
    void shouldReturnPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        ResponseEntity<Post> response = postService.getPostById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo("Title");
        verify(postRepository).findById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenPostDoesNotExist() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Post> response = postService.getPostById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(postRepository).findById(1L);
    }

    // ================= CREATE POST =================
    @Test
    void shouldCreatePost() {
        when(postRepository.save(any(Post.class))).thenReturn(post);

        ResponseEntity<Post> response = postService.createPost(post);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        verify(postRepository).save(any(Post.class));
    }

    // ================= UPDATE POST =================
    @Test
    void shouldUpdatePost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        ResponseEntity<Post> response = postService.updatePosts(1L, post);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(postRepository).findById(1L);
        verify(postRepository).save(post);
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingPost() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Post> response = postService.updatePosts(1L, post);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(postRepository).findById(1L);
        verify(postRepository, never()).save(any());
    }

    // ================= DELETE POST =================
    @Test
    void shouldDeletePost() {
        doNothing().when(postRepository).deleteById(1L);

        ResponseEntity<HttpStatus> response = postService.deletePost(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(postRepository).deleteById(1L);
    }

    // ================= DELETE ALL POSTS =================
    @Test
    void shouldDeleteAllPosts() {
        doNothing().when(postRepository).deleteAll();

        ResponseEntity<HttpStatus> response = postService.deleteAllPosts();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(postRepository).deleteAll();
    }

    // ================= FIND PUBLISHED POSTS =================
    @Test
    void shouldReturnPublishedPosts() {
        when(postRepository.findByPublished(true)).thenReturn(List.of(post));

        ResponseEntity<List<Post>> response = postService.findByPublished();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        verify(postRepository).findByPublished(true);
    }

    @Test
    void shouldReturnNoContentWhenNoPublishedPosts() {
        when(postRepository.findByPublished(true)).thenReturn(List.of());

        ResponseEntity<List<Post>> response = postService.findByPublished();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(postRepository).findByPublished(true);
    }
}
