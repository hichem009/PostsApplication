package com.exam.project.service;

import com.exam.project.model.Post;
import com.exam.project.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link PostService}.
 *
 * <p>
 * This service class contains the business logic for managing {@link Post}
 * entities. It communicates with the {@link PostRepository} to perform
 * CRUD operations such as create, read, update, and delete posts.
 * </p>
 *
 * <p>
 * All methods return {@link ResponseEntity} to properly handle HTTP
 * responses and status codes.
 * </p>
 */
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    /**
     * Constructor-based dependency injection for {@link PostRepository}.
     *
     * @param postRepository repository used to access Post data from the database
     */
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Retrieves all posts or filters posts by title.
     *
     * <p>
     * If the title is {@code null}, all posts are returned.
     * Otherwise, posts containing the given title (case-insensitive) are returned.
     * </p>
     *
     * @param title title to search for (optional)
     * @return {@link ResponseEntity} containing a list of posts and HTTP status:
     * <ul>
     *   <li>200 OK – posts found</li>
     *   <li>204 NO_CONTENT – no posts found</li>
     *   <li>500 INTERNAL_SERVER_ERROR – error occurred</li>
     * </ul>
     */
    @Override
    public ResponseEntity<List<Post>> getAllPosts(String title) {
        try {
            List<Post> posts = new ArrayList<>();

            if (title == null) {
                postRepository.findAll().forEach(posts::add);
            } else {
                postRepository.findByTitleContainingIgnoreCase(title).forEach(posts::add);
            }

            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a single post by its ID.
     *
     * @param id the ID of the post
     * @return {@link ResponseEntity} containing the post and HTTP status:
     * <ul>
     *   <li>200 OK – post found</li>
     *   <li>404 NOT_FOUND – post not found</li>
     * </ul>
     */
    @Override
    public ResponseEntity<Post> getPostById(long id) {
        Optional<Post> postData = postRepository.findById(id);

        return postData
                .map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Creates and saves a new post.
     *
     * <p>
     * The post is saved with {@code published = false} by default.
     * </p>
     *
     * @param post the post data to create
     * @return {@link ResponseEntity} containing the created post and HTTP status:
     * <ul>
     *   <li>201 CREATED – post successfully created</li>
     *   <li>500 INTERNAL_SERVER_ERROR – error occurred</li>
     * </ul>
     */
    @Override
    public ResponseEntity<Post> createPost(Post post) {
        try {
            Post savedPost = postRepository.save(
                    new Post(post.getTitle(), post.getDescription(), false)
            );
            return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing post by its ID.
     *
     * @param id   the ID of the post to update
     * @param post the updated post data
     * @return {@link ResponseEntity} containing the updated post and HTTP status:
     * <ul>
     *   <li>200 OK – post updated successfully</li>
     *   <li>404 NOT_FOUND – post not found</li>
     * </ul>
     */
    @Override
    public ResponseEntity<Post> updatePosts(long id, Post post) {
        Optional<Post> postData = postRepository.findById(id);

        if (postData.isPresent()) {
            Post existingPost = postData.get();
            existingPost.setTitle(post.getTitle());
            existingPost.setDescription(post.getDescription());
            existingPost.setPublished(post.isPublished());

            return new ResponseEntity<>(postRepository.save(existingPost), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a post by its ID.
     *
     * @param id the ID of the post to delete
     * @return {@link ResponseEntity} with HTTP status:
     * <ul>
     *   <li>204 NO_CONTENT – post deleted successfully</li>
     *   <li>500 INTERNAL_SERVER_ERROR – error occurred</li>
     * </ul>
     */
    @Override
    public ResponseEntity<HttpStatus> deletePost(long id) {
        try {
            postRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes all posts from the database.
     *
     * @return {@link ResponseEntity} with HTTP status:
     * <ul>
     *   <li>204 NO_CONTENT – all posts deleted</li>
     *   <li>500 INTERNAL_SERVER_ERROR – error occurred</li>
     * </ul>
     */
    @Override
    public ResponseEntity<HttpStatus> deleteAllPosts() {
        try {
            postRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all published posts.
     *
     * @return {@link ResponseEntity} containing a list of published posts and HTTP status:
     * <ul>
     *   <li>200 OK – published posts found</li>
     *   <li>204 NO_CONTENT – no published posts</li>
     *   <li>500 INTERNAL_SERVER_ERROR – error occurred</li>
     * </ul>
     */
    @Override
    public ResponseEntity<List<Post>> findByPublished() {
        try {
            List<Post> posts = postRepository.findByPublished(true);

            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

