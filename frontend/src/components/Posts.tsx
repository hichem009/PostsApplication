import { useState } from "react";
import { Post } from "../types/Post";
import { Link } from "react-router-dom";
import ConfirmModal from "./ConfirmModal";

// Props definition for the Posts component
interface PostsProps {
  posts: Post[]; // The full list of posts
  setPosts: React.Dispatch<React.SetStateAction<Post[]>>; // Function to update posts
  currentPage: number; // Current page number for pagination
  setCurrentPage: React.Dispatch<React.SetStateAction<number>>; // Function to change page
  postsPerPage: number; // Number of posts to show per page
  handleDelete: (id?: number) => void; // Function to delete a post
  loading: boolean;
  error: string;
  searchQuery: string; // The current search input
  setSearchQuery: React.Dispatch<React.SetStateAction<string>>; // Update search input
}

// Component to display the list of posts with pagination and search
export default function Posts({
  posts,
  setPosts,
  currentPage,
  setCurrentPage,
  postsPerPage,
  handleDelete,
  loading,
  error,
  searchQuery,
  setSearchQuery
}: PostsProps) {
  // Filter posts based on search query
  const filteredPosts = posts.filter(post =>
    post.title.toLowerCase().includes(searchQuery.toLowerCase())
  );

  // Calculate the index of the last post on the current page
  const indexOfLastPost = currentPage * postsPerPage;

  // Calculate the index of the first post on the current page
  const indexOfFirstPost = indexOfLastPost - postsPerPage;

  // Slice the filtered posts array to only show posts for the current page
  const currentPosts = filteredPosts.slice(indexOfFirstPost, indexOfLastPost);

  // Calculate the total number of pages for pagination based on filtered posts
  const totalPages = Math.ceil(filteredPosts.length / postsPerPage);


  const [modalOpen, setModalOpen] = useState(false);
  const [selectedPostId, setSelectedPostId] = useState<number | null>(null);


  const openDeleteModal = (id: number) => {
    setSelectedPostId(id);
    setModalOpen(true);
  };

  const confirmDelete = () => {
    if (selectedPostId) {
      handleDelete(selectedPostId);
      setModalOpen(false);
      setSelectedPostId(null);
    }
  };
  if (loading) return <p>Loading posts...</p>;
  if (error) return <p className="error">{error}</p>;

  return (
    <div className="posts-container">

      {/* Render each post in the current page */}
      {currentPosts.map(post => (
        <div key={post.id} className="post-card">
          <h3>{post.title}</h3>
          <p>{post.description}</p>
          <div className="actions">
            <Link to={`/posts/${post.id}`}>Details</Link>
            <Link to={`/update/${post.id}`}>Update</Link>
            <button onClick={() => post.id !== undefined && openDeleteModal(post.id)}>Delete</button>
          </div>
        </div>
      ))}

      {/* Pagination buttons */}
      <div className="pagination">
        {Array.from({ length: totalPages }, (_, i) => (
          <button
            key={i + 1}
            className={currentPage === i + 1 ? "active" : ""}
            onClick={() => setCurrentPage(i + 1)}
          >
            {i + 1}
          </button>
        ))}
      </div>

      {/* Delete Confirmation Modal */}
      <ConfirmModal
        isOpen={modalOpen}
        message="Are you sure you want to delete this post?"
        onConfirm={confirmDelete}
        onCancel={() => setModalOpen(false)}
      />
    </div>
  );
}
