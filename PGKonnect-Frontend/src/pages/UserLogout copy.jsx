import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const UserLogout = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Remove session items
    sessionStorage.removeItem("active-admin");
    sessionStorage.removeItem("admin-jwtToken");

    sessionStorage.removeItem("active-owner");
    sessionStorage.removeItem("owner-jwtToken");

    sessionStorage.removeItem("active-guest");
    sessionStorage.removeItem("giest-jwtToken");

    // Set loading state to false and redirect after a delay
    setTimeout(() => {
      setLoading(false); // Hide the loading message

      // Redirect to home and force a page reload
      window.location.href = "/home";
    }, 1000); // Adjust delay as needed
  }, [navigate]);

  return (
    <div className="container my-5 text-center">
      {loading ? (
        <>
          <p>Logging out...</p>
          <div className="spinner-border" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
        </>
      ) : (
        <p>You are being redirected...</p>
      )}
    </div>
  );
};

export default UserLogout;
