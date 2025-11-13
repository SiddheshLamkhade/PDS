import React from "react";
import { NavLink } from "react-router-dom";
import logo from "../assets/ashokstmabh-logo.png";
import "../styles/Navbar.css";

const Navbar = () => {
  return (
    <header className="navbar">
      <div className="nav-left">
        <img src={logo} alt="Government Logo" className="gov-logo" />
        <div className="nav-title">
          <h1>E-Ration Portal</h1>
          <p>Department of Food, Civil Supplies and Consumer Affairs</p>
        </div>
      </div>

      <nav className="nav-links">
        <NavLink to="/" end>
          Home
        </NavLink>
        <NavLink to="/register">Register</NavLink>
        <NavLink to="/login">Login</NavLink>
      </nav>
    </header>
  );
};

export default Navbar;
