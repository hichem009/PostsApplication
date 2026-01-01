import axios from "axios";
import { Post } from "../types/Post";

// Base URL for the API
//const BASE_URL = "https://jsonplaceholder.typicode.com";
const BASE_URL = "http://localhost:9098/api";

// =====================
// API FUNCTIONS
// =====================

// Fetch all posts from the API
// Returns a Promise with an array of Post objects
export const getAllPosts = () => 
  axios.get<Post[]>(`${BASE_URL}/posts`);

// Fetch a single post by its ID
// Returns a Promise with a single Post object
export const getPostById = (id: number) => 
  axios.get<Post>(`${BASE_URL}/posts/${id}`);

// Add a new post
// 'post' must have title, body, and userId, but no id (API will assign it)
// Returns a Promise with the newly created Post object
export const addPost = (post: Omit<Post, "id">) => 
  axios.post<Post>(`${BASE_URL}/posts`, post);

// Edit an existing post by ID
// 'post' must have title, body, and userId, but no id
// Returns a Promise with the updated Post object
export const editPost = (id: number, post: Omit<Post, "id">) => 
  axios.put<Post>(`${BASE_URL}/posts/${id}`, post);

// Delete a post by its ID
// Returns a Promise for deletion (no data returned)
export const deletePost = (id: number) => 
  axios.delete(`${BASE_URL}/posts/${id}`);
