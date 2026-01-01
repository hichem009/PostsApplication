import { useState } from "react";
import { addPost } from "../services/api";
import { useNavigate } from "react-router-dom";
import { Post } from "../types/Post";

// Define the props for this component
// onAddPost is a function passed from parent to add the new post to the top of the list
interface AddPostProps {
  onAddPost: (newPost: Post) => void;
}

export default function AddPost({ onAddPost }: AddPostProps) {
  // Local state to store the values of the form fields
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [error, setError] = useState(""); // added

  // useNavigate is a React Router hook to programmatically navigate to another page
  const navigate = useNavigate();

  // This function is triggered when the form is submitted
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault(); // Prevent the default form submission behavior (page reload)

    // added validation
    if (!title.trim() || !description.trim()) {
      setError("Title and body must not be empty");
      return;
    }

    if (/^\d+$/.test(title.trim()) || /^\d+$/.test(description.trim())) {
      setError("Title and body must not contain only numbers");
      return;
    }

    setError(""); // clear error

    // Call the API to create a new post
    // We pass an object containing title, body, and a fixed userId
    const response = await addPost({ title, description, userId: 1 });

    // Add the newly created post to the top of the posts list
    // This is done by calling the function passed from the parent component
    onAddPost(response.data);

    // Clear the input fields after submission
    setTitle("");
    setDescription("");

    // Navigate back to the main posts page
    navigate("/");
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Add Post</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {/* Input field for post title */}
      <input
        placeholder="Title"
        value={title} // Bind the input value to the title state
        onChange={e => setTitle(e.target.value)} // Update state when user types
      />

      {/* Textarea for post body/content */}
      <textarea
        placeholder="Body"
        value={description} // Bind the textarea value to the body state
        onChange={e => setDescription(e.target.value)} // Update state when user types
      />

      {/* Submit button */}
      <button type="submit">Add</button>
    </form>
  );
}
