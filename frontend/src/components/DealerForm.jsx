


import React, { useState } from "react";
import api from "../api/axios";
import "./DealerForm.css";
import bgImage from "../assets/bacground-img.png"; // background image
import logo from "../assets/ashokstmabh-logo.png"; // project logo

export default function DealerForm() {
  const [dealerData, setDealerData] = useState({
    dealerName: "",
    shopLocation: "",
    contactInfo: "",
  });

  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setDealerData({
      ...dealerData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");

    try {
      const res = await api.post("/users/dealers", dealerData);
      setMessage("✅ Dealer added successfully!");
      setDealerData({ dealerName: "", shopLocation: "", contactInfo: "" });
    } catch (err) {
      setMessage("❌ Failed to add dealer. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      className="dealer-form-bg"
      style={{ backgroundImage: `url(${bgImage})` }}
    >
      <div className="dealer-form-wrapper">
        <div className="dealer-form-container">
          <div className="dealer-header">
            <img src={logo} alt="E-Ration Logo" className="dealer-logo" />
            <h1 className="dealer-title">E-Ration Dealer Registration</h1>
            <p className="dealer-subtitle">
              Public Distribution System • Government of India
            </p>
          </div>

          <form onSubmit={handleSubmit} className="dealer-form">
            <div className="form-group">
              <label>Dealer Name</label>
              <input
                type="text"
                name="dealerName"
                value={dealerData.dealerName}
                onChange={handleChange}
                placeholder="Enter dealer name"
                required
              />
            </div>

            <div className="form-group">
              <label>Shop Location</label>
              <input
                type="text"
                name="shopLocation"
                value={dealerData.shopLocation}
                onChange={handleChange}
                placeholder="Enter shop location"
                required
              />
            </div>

            <div className="form-group">
              <label>Contact Info</label>
              <input
                type="text"
                name="contactInfo"
                value={dealerData.contactInfo}
                onChange={handleChange}
                placeholder="Enter contact number"
                required
              />
            </div>

            <button type="submit" disabled={loading}>
              {loading ? "Submitting..." : "Add Dealer"}
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
