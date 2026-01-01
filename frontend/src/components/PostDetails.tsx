import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getPostById } from "../services/api";
import { Post } from "../types/Post";
import Loader from "./Loader"; // Your loader component
import Toast from "./Toast";   // Optional toast component

export default function PostDetails() {
  const { id } = useParams();

  const [post, setPost] = useState<Post | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showToast, setShowToast] = useState(false);

  const triggerToast = (msg: string) => {
    setError(msg);
    setShowToast(true);
    setTimeout(() => setShowToast(false), 3000);
  };

  useEffect(() => {
    if (!id) return;

    setLoading(true);
    setError("");
    
    getPostById(Number(id))
      .then(res => setPost(res.data))
      .catch(() => triggerToast("Post does not exist"))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <Loader />;

  return (
    <div className="post-details-container">

      {post && (
        <div className="post-card animate-fade">
          <h2 className="post-title">{post.title}</h2>
          <p className="post-body">{post.description}</p>
        </div>
      )}
    </div>
  );
}
