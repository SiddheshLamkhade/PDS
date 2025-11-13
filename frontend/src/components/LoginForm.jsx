import React, { useState } from "react";
import axios from "axios";
import "../styles/LoginForm.css";

const LoginForm = () => {
  const [role, setRole] = useState("CITIZEN");
  const [emailOrUsername, setEmailOrUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      let url = "";
      let payload = { emailOrUsername, password };

      if (role === "CITIZEN") {
        url = "http://localhost:5000/api/citizens/login";
      } else if (role === "DEALER") {
        url = "http://localhost:5000/api/dealers/login";
      } else if (role === "ADMIN") {
        url = "http://localhost:5000/api/users/login";
      }

      const response = await axios.post(url, payload);
      setMessage(`✅ ${role} logged in successfully!`);

      localStorage.setItem("token", response.data.token);
      localStorage.setItem("role", role);

      // Redirect based on role
      if (role === "CITIZEN") {
        window.location.href = "/citizen/dashboard";
      } else if (role === "DEALER") {
        window.location.href = "/dealer/dashboard";
      } else {
        window.location.href = "/admin/dashboard";
      }
    } catch (error) {
      console.error("Login failed:", error);
      setMessage("❌ Invalid credentials or login failed.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-background">
      <div className="login-form-container">
        <h2 className="form-title">User Login</h2>

        <form onSubmit={handleSubmit} className="login-form">
          <label>
            Select Role:
            <select
              value={role}
              onChange={(e) => setRole(e.target.value)}
              className="form-select"
            >
              <option value="CITIZEN">Citizen</option>
              <option value="DEALER">Dealer</option>
              <option value="ADMIN">Admin</option>
            </select>
          </label>

          <label>
            Email or Username:
            <input
              type="text"
              value={emailOrUsername}
              onChange={(e) => setEmailOrUsername(e.target.value)}
              required
            />
          </label>

          <label>
            Password:
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </label>

          <button type="submit" className="submit-btn" disabled={loading}>
            {loading ? "Logging in..." : "Login"}
          </button>
        </form>

        {message && <p className="response-message">{message}</p>}
      </div>
    </div>
  );
};

export default LoginForm;
