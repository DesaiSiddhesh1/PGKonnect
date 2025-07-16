import { useState } from "react";

const ForgotPasswordLink = () => {
    const [email, setEmail] = useState("");
    const [message, setMessage] = useState("");
    const [submitted, setSubmitted] = useState(false);
    const [error, setError] = useState("");

    const sendResetLink = () => {
        // 🔌 Check if internet is available
        if (!navigator.onLine) {
            setError("No internet connection. Please connect and try again.");
            return;
        }

        fetch("http://localhost:8080/api/user/forgot-password", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email }),
        })
            .then(() => {
                setMessage(
                    "If the email is linked with any account on our site, a password reset link has been sent."
                );
                setSubmitted(true);
                setError(""); // clear any previous error
            })
            .catch(() => {
                setError("Unable to reach server. Please check your internet connection.");
            });
    };

    return (
        <div className="container">
            <h3>Forgot Password</h3>
            {!submitted ? (
                <>
                    <input
                        type="email"
                        placeholder="Enter your email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <br />
                    <button onClick={sendResetLink}>Send Reset Link</button>
                    {error && <p style={{ color: "red", marginTop: "10px" }}>{error}</p>}
                </>
            ) : (
                <p style={{ color: "green", marginTop: "10px" }}>{message}</p>
            )}
        </div>
    );
};

export default ForgotPasswordLink;
