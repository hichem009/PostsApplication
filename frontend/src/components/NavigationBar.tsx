import { Link, useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
interface NavigationBarProps {
    searchQuery: string;
    setSearchQuery: (query: string) => void;
  }
  
  export default function NavigationBar({ searchQuery, setSearchQuery }: NavigationBarProps) {
    const [searchActive, setSearchActive] = useState(false);
    const location = useLocation();
    const navigate = useNavigate();
  
    // Detect if we are on the post details page
    const isDetailsPage = location.pathname.startsWith("/posts/") || location.pathname.startsWith("/add")
     || location.pathname.startsWith("/update/");

  return (
    <nav className="navbar">
      {/* App logo/title */}
      <div className="logo-container">
        {isDetailsPage ? (
          <button className="back-btn" onClick={() => navigate(-1)}>
            ← Back
          </button>
        ) : (
          <h1 className="logo">Posts Manager</h1>
        )}
      </div>

      {/* Navigation links/buttons + search */}
      <div className="nav-actions">
        <Link to="/" className="nav-link">Home</Link>
        <Link to="/add" className="add-btn">Add New Post</Link>

        {/* Search wrapper */}
        <div
          className={`search-wrapper ${searchActive ? "active" : ""}`}
          onClick={() => setSearchActive(true)}
        >
          <span className="search-icon">🔍</span>
          <input
            type="text"
            placeholder="Search posts..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="search-input"
            onBlur={() => setSearchActive(false)}
          />
        </div>
      </div>
    </nav>
  );
}
