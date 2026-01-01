import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import { getAllPosts, getPostById, addPost, editPost, deletePost } from "../services/api";
import { Post } from "../types/Post";

const mock = new MockAdapter(axios);

describe("API Service", () => {
  afterEach(() => {
    mock.reset();
  });

  it("should fetch all posts", async () => {
    const mockPosts: Post[] = [
      { id: 1, title: "Test Post", description: "Body", userId: 1 },
    ];

    mock.onGet("https://jsonplaceholder.typicode.com/posts").reply(200, mockPosts);

    const response = await getAllPosts();
    expect(response.data).toEqual(mockPosts);
  });

  it("should fetch a post by id", async () => {
    const mockPost: Post = { id: 1, title: "Test Post", description: "Body", userId: 1 };

    mock.onGet("https://jsonplaceholder.typicode.com/posts/1").reply(200, mockPost);

    const response = await getPostById(1);
    expect(response.data).toEqual(mockPost);
  });

  it("should add a new post", async () => {
    const newPost = { title: "New", description: "Content", userId: 1 };
    const returnedPost = { id: 101, ...newPost };

    mock.onPost("https://jsonplaceholder.typicode.com/posts", newPost).reply(201, returnedPost);

    const response = await addPost(newPost);
    expect(response.data).toEqual(returnedPost);
  });

  it("should edit a post", async () => {
    const updatedPost = { title: "Updated", description: "Updated body", userId: 1 };

    mock.onPut("https://jsonplaceholder.typicode.com/posts/1", updatedPost)
      .reply(200, { id: 1, ...updatedPost });

    const response = await editPost(1, updatedPost);
    expect(response.data).toEqual({ id: 1, ...updatedPost });
  });

  it("should delete a post", async () => {
    mock.onDelete("https://jsonplaceholder.typicode.com/posts/1").reply(200);

    const response = await deletePost(1);
    expect(response.status).toBe(200);
  });
});
