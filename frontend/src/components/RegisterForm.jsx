import React, { useState } from "react";
import axios from "axios";
import "../styles/RegisterForm.css"; // CSS styling

const RegisterForm = () => {
  const [role, setRole] = useState("CITIZEN");
  const [formData, setFormData] = useState({
    fullName: "",
    username: "",
    email: "",
    password: "",
    rationCardNo: "",
    address: "",
    phoneNumber: "",
    familySize: "",
    category: "",
    dealerId: "",
    shopName: "",
    shopLicense: "",
    region: "",
  });

  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleRoleChange = (e) => {
    setRole(e.target.value);
    setMessage("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      let url = "";
      let payload = {};

      if (role === "CITIZEN") {
        url = "http://localhost:8080/api/citizens";
        payload = {
          name: formData.fullName,
          username: formData.username,
          email: formData.email,
          password: formData.password,
          rationCardNumber: formData.rationCardNo,
          address: formData.address,
          phoneNumber: formData.phoneNumber,
          familySize: formData.familySize,
          category: formData.category,
          dealerId: formData.dealerId,
        };
      } else if (role === "DEALER") {
        url = "http://localhost:8080/api/dealers";
        payload = {
          name: formData.fullName,
          username: formData.username,
          email: formData.email,
          password: formData.password,
          shopName: formData.shopName,
          shopLicense: formData.shopLicense,
          region: formData.region,
          address: formData.address,
          phoneNumber: formData.phoneNumber,
        };
      } else if (role === "ADMIN") {
        url = "http://localhost:8080/api/users";
        payload = {
          fullName: formData.fullName,
          username: formData.username,
          email: formData.email,
          password: formData.password,
          role: "ADMIN",
        };
      }

      const response = await axios.post(url, payload);
      setMessage(`${role} registered successfully!`);
      console.log("Response:", response.data);

      setFormData({
        fullName: "",
        username: "",
        email: "",
        password: "",
        rationCardNo: "",
        address: "",
        phoneNumber: "",
        familySize: "",
        category: "",
        dealerId: "",
        shopName: "",
        shopLicense: "",
        region: "",
      });
    } catch (error) {
      console.error("Error:", error);
      setMessage("❌ Registration failed: " + (error.response?.data?.message || error.message));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-background">
      <div className="register-form-container">
        <h2 className="form-title">User Registration</h2>

        <form onSubmit={handleSubmit} className="register-form">
          {/* Role Selection */}
          <label>
            Select Role:
            <select value={role} onChange={handleRoleChange} className="form-select">
              <option value="CITIZEN">Citizen</option>
              <option value="DEALER">Dealer</option>
              <option value="ADMIN">Admin</option>
            </select>
          </label>

          <label>
            Full Name:
            <input
              type="text"
              name="fullName"
              value={formData.fullName}
              onChange={handleChange}
              required
            />
          </label>

          <label>
            Username:
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              required
            />
          </label>

          <label>
            Email:
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </label>

          <label>
            Password:
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </label>

          {/* Conditional Role Fields */}
          {role === "CITIZEN" && (
            <>
              <label>
                Ration Card Number:
                <input
                  type="text"
                  name="rationCardNo"
                  value={formData.rationCardNo}
                  onChange={handleChange}
                  required
                />
              </label>

              <label>
                Address:
                <input
                  type="text"
                  name="address"
                  value={formData.address}
                  onChange={handleChange}
                  required
                />
              </label>

              <label>
                Phone Number:
                <input
                  type="text"
                  name="phoneNumber"
                  value={formData.phoneNumber}
                  onChange={handleChange}
                  required
                />
              </label>

              <label>
                Family Size:
                <input
                  type="number"
                  name="familySize"
                  value={formData.familySize}
                  onChange={handleChange}
                  required
                />
              </label>

              <label>
                Category:
                <select
                  name="category"
                  value={formData.category}
                  onChange={handleChange}
                  required
                >
                  <option value="">-- Select Category --</option>
                  <option value="APL">APL</option>
                  <option value="BPL">BPL</option>
                  <option value="AAY">AAY</option>
                </select>
              </label>

              <label>
                Dealer ID (optional):
                <input
                  type="text"
                  name="dealerId"
                  value={formData.dealerId}
                  onChange={handleChange}
                />
              </label>
            </>
          )}

          {role === "DEALER" && (
            <>
              <label>
                Shop Name:
                <input
                  type="text"
                  name="shopName"
                  value={formData.shopName}
                  onChange={handleChange}
                  required
                />
              </label>

              <label>
                Shop License:
                <input
                  type="text"
                  name="shopLicense"
                  value={formData.shopLicense}
                  onChange={handleChange}
                  required
                />
              </label>

              <label>
                Region:
                <input
                  type="text"
                  name="region"
                  value={formData.region}
                  onChange={handleChange}
                  required
                />
              </label>

              <label>
                Address:
                <input
                  type="text"
                  name="address"
                  value={formData.address}
                  onChange={handleChange}
                  required
                />
              </label>

              <label>
                Phone Number:
                <input
                  type="text"
                  name="phoneNumber"
                  value={formData.phoneNumber}
                  onChange={handleChange}
                  required
                />
              </label>
            </>
          )}

          <button type="submit" className="submit-btn" disabled={loading}>
            {loading ? "Registering..." : "Register"}
          </button>
        </form>

        {message && <p className="response-message">{message}</p>}
      </div>
    </div>
  );
};

export default RegisterForm;
