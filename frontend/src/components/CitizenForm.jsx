import React, { useState } from "react";
import api from "../api/axios";
import "./CitizenForm.css";
import bgImage from "../assets/bacground-img.png"; 
import logo from "../assets/ashokstmabh-logo.png"; 

export default function CitizenForm() {
  const [citizenData, setCitizenData] = useState({
    name: "",
    rationCardNo: "",
    familySize: "",
    category: "",
    dealerId: "",
  });

  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setCitizenData({
      ...citizenData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      await api.post("/users/citizens", {
        ...citizenData,
        familySize: Number(citizenData.familySize),
        dealerId: Number(citizenData.dealerId),
      });

      setMessage("✅ Citizen registered successfully!");
      setCitizenData({
        name: "",
        rationCardNo: "",
        familySize: "",
        category: "",
        dealerId: "",
      });
    } catch (err) {
      setMessage("❌ Failed to register citizen. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      className="citizen-form-bg"
      style={{ backgroundImage: `url(${bgImage})` }}
    >
      <div className="citizen-form-wrapper">
        <div className="citizen-form-container">
          <div className="citizen-header">
            <img src={logo} alt="E-Ration Logo" className="citizen-logo" />
            <h1 className="citizen-title">E-Ration Citizen Registration</h1>
            <p className="citizen-subtitle">
              Public Distribution System • Government of India
            </p>
          </div>

          <form onSubmit={handleSubmit} className="citizen-form">
            <div className="form-group">
              <label>Citizen Name</label>
              <input
                type="text"
                name="name"
                value={citizenData.name}
                onChange={handleChange}
                placeholder="Enter full name"
                required
              />
            </div>

            <div className="form-group">
              <label>Ration Card Number</label>
              <input
                type="text"
                name="rationCardNo"
                value={citizenData.rationCardNo}
                onChange={handleChange}
                placeholder="Enter ration card number"
                required
              />
            </div>

            <div className="form-group">
              <label>Family Size</label>
              <input
                type="number"
                name="familySize"
                value={citizenData.familySize}
                onChange={handleChange}
                placeholder="Enter number of family members"
                required
                min="1"
              />
            </div>

            <div className="form-group">
              <label>Category</label>
              <select
                name="category"
                value={citizenData.category}
                onChange={handleChange}
                required
              >
                <option value="">-- Select Category --</option>
                <option value="APL">APL (Above Poverty Line)</option>
                <option value="BPL">BPL (Below Poverty Line)</option>
                <option value="AAY">AAY (Antyodaya Anna Yojana)</option>
              </select>
            </div>

            <div className="form-group">
              <label>Dealer ID</label>
              <input
                type="number"
                name="dealerId"
                value={citizenData.dealerId}
                onChange={handleChange}
                placeholder="Enter assigned dealer ID"
                required
                min="1"
              />
            </div>

            <button type="submit" disabled={loading}>
              {loading ? "Submitting..." : "Register Citizen"}
            </button>
          </form>

          {message && (
            <p
              className={`message ${
                message.includes("✅") ? "success" : "error"
              }`}
            >
              {message}
            </p>
          )}
        </div>
      </div>
    </div>
  );
}
