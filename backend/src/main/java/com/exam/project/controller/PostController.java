package com.exam.project.controller;

import java.util.List;

import com.exam.project.model.Post;
import com.exam.project.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link Post} resources.
 *
 * <p>
 * This controller exposes REST APIs to create, retrieve, update,
 * delete, and filter posts.
 * </p>
 *
 * <p>
 * All requests are handled under the base path <b>/api</b>.
 * </p>
 */
@Tag(name = "Post API", description = "Operations related to Posts management")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class PostController {

  private final PostService postService;

  /**
   * Constructor-based dependency injection of {@link PostService}.
   *
   * @param postService service layer handling business logic
   */
  public PostController(PostService postService) {
    this.postService = postService;
  }

  /**
   * Retrieves all posts or filters posts by title.
   *
   * @param title optional title to filter posts
   * @return list of posts or NO_CONTENT if none found
   */
  @Operation(
          summary = "Get all posts",
          description = "Retrieve all posts or filter posts by title (case-insensitive)",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Posts retrieved successfully",
                          content = @Content(schema = @Schema(implementation = Post.class))),
                  @ApiResponse(responseCode = "204", description = "No posts found"),
                  @ApiResponse(responseCode = "500", description = "Internal server error")
          }
  )
  @GetMapping("/posts")
  public ResponseEntity<List<Post>> getAllPosts(
          @RequestParam(required = false) String title) {
    return postService.getAllPosts(title);
  }

  /**
   * Retrieves a post by its ID.
   *
   * @param id post ID
   * @return the post if found, otherwise NOT_FOUND
   */
  @Operation(
          summary = "Get post by ID",
          description = "Retrieve a single post using its ID",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Post found"),
                  @ApiResponse(responseCode = "404", description = "Post not found")
          }
  )
  @GetMapping("/posts/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable("id") long id) {
    return postService.getPostById(id);
  }

  /**
   * Creates a new post.
   *
   * @param post post data to create
   * @return created post with CREATED status
   */
  @Operation(
          summary = "Create a new post",
          description = "Create and save a new post",
          responses = {
                  @ApiResponse(responseCode = "201", description = "Post created successfully"),
                  @ApiResponse(responseCode = "500", description = "Internal server error")
          }
  )
  @PostMapping("/posts")
  public ResponseEntity<Post> createPost(@RequestBody Post post) {
    return postService.createPost(post);
  }

  /**
   * Updates an existing post.
   *
   * @param id   post ID
   * @param post updated post data
   * @return updated post or NOT_FOUND if post does not exist
   */
  @Operation(
          summary = "Update a post",
          description = "Update an existing post using its ID",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Post updated successfully"),
                  @ApiResponse(responseCode = "404", description = "Post not found")
          }
  )
  @PutMapping("/posts/{id}")
  public ResponseEntity<Post> updatePost(
          @PathVariable("id") long id,
          @RequestBody Post post) {
    return postService.updatePosts(id, post);
  }

  /**
   * Deletes a post by its ID.
   *
   * @param id post ID
   * @return NO_CONTENT if deleted successfully
   */
  @Operation(
          summary = "Delete a post",
          description = "Delete a post by its ID",
          responses = {
                  @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
                  @ApiResponse(responseCode = "500", description = "Internal server error")
          }
  )
  @DeleteMapping("/posts/{id}")
  public ResponseEntity<HttpStatus> deletePost(@PathVariable("id") long id) {
    return postService.deletePost(id);
  }

  /**
   * Deletes all posts.
   *
   * @return NO_CONTENT if all posts are deleted
   */
  @Operation(
          summary = "Delete all posts",
          description = "Remove all posts from the database",
          responses = {
                  @ApiResponse(responseCode = "204", description = "All posts deleted"),
                  @ApiResponse(responseCode = "500", description = "Internal server error")
          }
  )
  @DeleteMapping("/posts")
  public ResponseEntity<HttpStatus> deleteAllPosts() {
    return postService.deleteAllPosts();
  }

  /**
   * Retrieves all published posts.
   *
   * @return list of published posts
   */
  @Operation(
          summary = "Get published posts",
          description = "Retrieve all posts with published status = true",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Published posts retrieved"),
                  @ApiResponse(responseCode = "204", description = "No published posts found"),
                  @ApiResponse(responseCode = "500", description = "Internal server error")
          }
  )
  @GetMapping("/posts/published")
  public ResponseEntity<List<Post>> findByPublished() {
    return postService.findByPublished();
  }
}
