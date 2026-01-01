package com.exam.project.controller;

import com.exam.project.model.Post;
import com.exam.project.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    // ================= GET ALL POSTS =================
    @Test
    void shouldReturnAllPosts() throws Exception {
        List<Post> posts = List.of(
                new Post("Title 1", "Desc 1", true),
                new Post("Title 2", "Desc 2", false)
        );

        when(postService.getAllPosts(null))
                .thenReturn(new ResponseEntity<>(posts, HttpStatus.OK));

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(postService).getAllPosts(null);
    }

    // ================= GET POST BY ID =================
    @Test
    void shouldReturnPostById() throws Exception {
        Post post = new Post("Spring", "Spring Boot Post", true);

        when(postService.getPostById(1L))
                .thenReturn(new ResponseEntity<>(post, HttpStatus.OK));

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring"));

        verify(postService).getPostById(1L);
    }

    // ================= CREATE POST =================
    @Test
    void shouldCreatePost() throws Exception {
        Post post = new Post("New Post", "New Description", false);

        when(postService.createPost(any(Post.class)))
                .thenReturn(new ResponseEntity<>(post, HttpStatus.CREATED));

        mockMvc.perform(post("/api/posts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Post"));

        verify(postService).createPost(any(Post.class));
    }

    // ================= UPDATE POST =================
    @Test
    void shouldUpdatePost() throws Exception {
        Post updatedPost = new Post("Updated", "Updated Desc", true);

        when(postService.updatePosts(eq(1L), any(Post.class)))
                .thenReturn(new ResponseEntity<>(updatedPost, HttpStatus.OK));

        mockMvc.perform(put("/api/posts/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));

        verify(postService).updatePosts(eq(1L), any(Post.class));
    }

    // ================= DELETE POST =================
    @Test
    void shouldDeletePost() throws Exception {
        when(postService.deletePost(1L))
                .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNoContent());

        verify(postService).deletePost(1L);
    }

    // ================= GET PUBLISHED POSTS =================
    @Test
    void shouldReturnPublishedPosts() throws Exception {
        List<Post> posts = List.of(
                new Post("Published", "Yes", true)
        );

        when(postService.findByPublished())
                .thenReturn(new ResponseEntity<>(posts, HttpStatus.OK));

        mockMvc.perform(get("/api/posts/published"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(postService).findByPublished();
    }
}