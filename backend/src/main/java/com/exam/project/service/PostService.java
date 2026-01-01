package com.exam.project.service;

import com.exam.project.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface PostService {

    public ResponseEntity<List<Post>> getAllPosts(String title) ;

    public ResponseEntity<Post> getPostById(long id);

    public ResponseEntity<Post> createPost(Post tutorial);

    public ResponseEntity<Post> updatePosts(long id, Post tutorial);

    public ResponseEntity<HttpStatus> deletePost(long id);

    public ResponseEntity<HttpStatus> deleteAllPosts();

    public ResponseEntity<List<Post>> findByPublished();
}
