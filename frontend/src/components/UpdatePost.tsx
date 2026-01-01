import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { editPost, getPostById } from "../services/api";
import { Post } from "../types/Post";

// Props definition for UpdatePost
interface UpdatePostProps {
    posts: Post[]; // add this

  // Callback function to update the post in the parent state
  onUpdatePost: (updatedPost: Post) => void;
}

// Component for updating/editing a post
export default function UpdatePost({ onUpdatePost }: UpdatePostProps) {
  // Get the post ID from the URL parameters
  const { id } = useParams<{ id: string }>();
  const postId = Number(id); // Convert ID to number

  // Local state for the post's title and body
  const [title, setTitle] = useState("");
  const [description, setBody] = useState("");

  // useNavigate hook allows us to programmatically navigate
  const navigate = useNavigate();

  // Fetch the post details when the component mounts or when postId changes
  useEffect(() => {
    if (!postId) return;
  
    // Try to get the post from local state first
    const localPost = (window as any).posts?.find((p: Post) => p.id === postId);
    if (localPost) {
      setTitle(localPost.title);
      setBody(localPost.description);
    } else {
      // Fallback: fetch from API if not in local state
      getPostById(postId).then(res => {
        setTitle(res.data.title);
        setBody(res.data.description);
      });
    }
  }, [postId]);
  

  // Handle form submission to update the post
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!postId) return; // Do nothing if ID is invalid

    // Call API to update the post
    const response = await editPost(postId, { title, description, userId: 1 });

    // Pass the updated post to parent to update the posts list
    onUpdatePost(response.data); // This can also move it to the top of the list

    // Navigate back to the main posts list
    navigate("/");
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Update Post</h2>

      {/* Input for post title */}
      <input
        placeholder="Title"
        value={title}
        onChange={e => setTitle(e.target.value)}
        required
      />

      {/* Textarea for post body */}
      <textarea
        placeholder="Body"
        value={description}
        onChange={e => setBody(e.target.value)}
        required
      />

      {/* Submit button to update the post */}
      <button type="submit">Update</button>
    </form>
  );
}
