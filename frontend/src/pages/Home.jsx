import React from "react";
import { Link } from "react-router-dom";
import "./Home.css";
import bgImage from "../assets/bacground-img.png"; // background image

const Home = () => {
  return (
    <section
      className="home-background"
      style={{
        backgroundImage: `url(${bgImage})`,
      }}
    >
      <div className="home-overlay">
        <div className="home-content">
          <h1 className="home-title">E-Ration Distribution Portal</h1>
          <h3 className="home-subheading">
            Department of Food, Civil Supplies and Consumer Affairs
          </h3>
          <p className="home-subtitle">
            Empowering Citizens through Transparent and Efficient Public Distribution System
          </p>

          <div className="home-buttons">
            <Link to="/register" className="home-btn primary-btn">
              Register Now
            </Link>
            <Link to="/login" className="home-btn secondary-btn">
              Login
            </Link>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Home;
