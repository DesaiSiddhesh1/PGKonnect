import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";

const ForgotPassword = () => {
    const [email, setEmail] = useState("");
    const [otp, setOtp] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [step, setStep] = useState(1);

    const sendOtp = () => {
        fetch("http://localhost:8080/api/user/forgot-password", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email }),
        })
            .then(async (res) => {
                const data = await res.json();
                if (res.ok) {
                    toast.success(data.message);
                    setStep(2); // ✅ move only if OTP sent
                } else {
                    toast.error(data.message || "Failed to send OTP");
                }
            })
            .catch(() => toast.error("Server error. Please try again."));
    };


    const resetPassword = () => {
        fetch("http://localhost:8080/api/user/reset-password", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ emailId: email, otp, newPassword }),
        })
            .then((res) => res.json())
            .then((data) => {
                toast.success(data.message);
                setStep(1);
            })
            .catch(() => toast.error("Invalid OTP or error occurred"));
    };

    return (
        <div className="container">
            {step === 1 ? (
                <>
                    <h4>Forgot Password</h4>
                    <input type="email" placeholder="Enter Email" value={email} onChange={(e) => setEmail(e.target.value)} />
                    <button onClick={sendOtp}>Send OTP</button>
                </>
            ) : (
                <>
                    <h4>Enter OTP & Reset Password</h4>
                    <input type="text" placeholder="Enter OTP" value={otp} onChange={(e) => setOtp(e.target.value)} />
                    <input type="password" placeholder="New Password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} />
                    <button onClick={resetPassword}>Reset Password</button>
                </>
            )}
            <ToastContainer />
        </div>
    );
};

export default ForgotPassword;
