import { useState } from "react";
import { useSearchParams, Link } from "react-router-dom";

const ResetPassword = () => {
    const [searchParams] = useSearchParams();
    const token = searchParams.get("token");

    const [newPassword, setNewPassword] = useState("");
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState("");

    const resetPassword = () => {
        // Check internet connection
        if (!navigator.onLine) {
            setError("No internet connection. Please connect and try again.");
            return;
        }

        fetch("http://localhost:8080/api/user/reset-password", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ token, newPassword }),
        })
            .then(async (res) => {
                const data = await res.json();
                if (res.ok) {
                    setSuccess(true);
                    setError(""); // Clear error if success
                } else {
                    setError(data.message || "Failed to reset password");
                }
            })
            .catch(() => {
                setError("Unable to reach server. Please check your internet connection.");
            });
    };

    return (
        <div className="container">
            <h3>Reset Your Password</h3>

            {success ? (
                <p style={{ color: "green", marginTop: "10px" }}>
                    Your password has been changed. Please{" "}
                    <Link to="/user/login">click here</Link> to login.
                </p>
            ) : (
                <>
                    <input
                        type="password"
                        placeholder="Enter new password"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                    />
                    <br />
                    <button onClick={resetPassword}>Update Password</button>
                    {error && <p style={{ color: "red", marginTop: "10px" }}>{error}</p>}
                </>
            )}
        </div>
    );
};

export default ResetPassword;
