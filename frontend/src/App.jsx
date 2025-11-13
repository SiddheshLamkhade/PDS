// import React from "react";
// import { BrowserRouter as Router, Routes, Route, NavLink } from "react-router-dom";
// import DealerForm from "./components/DealerForm";
// import CitizenForm from "./components/CitizenForm";
// import Home from "./pages/Home";
// import logo from "./assets/ashokstmabh-logo.png"; 
// import "./App.css";

// const App = () => {
//   return (
//     <Router>
//       <header className="navbar">
//         <div className="nav-left">
//           <img
//             src={logo}
//             alt="E-Ration Logo"
//             className="gov-logo"
//           />
//           <div className="nav-title">
//             <h1>E-Ration Portal</h1>
//             <p>Department of Food, Civil Supplies and Consumer Affairs</p>
//           </div>
//         </div>

//         <nav className="nav-links">
//           <NavLink to="/" end>
//             Home
//           </NavLink>
//           <NavLink to="/dealer">Dealer Registration</NavLink>
//           <NavLink to="/citizen">Citizen Registration</NavLink>
//         </nav>
//       </header>

//       <Routes>
//         <Route path="/" element={<Home />} />
//         <Route path="/dealer" element={<DealerForm />} />
//         <Route path="/citizen" element={<CitizenForm />} />
//       </Routes>
//     </Router>
//   );
// };

// export default App;


import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import RegisterForm from "./components/RegisterForm.jsx";
import Home from "./pages/Home";
import LoginForm from "./components/LoginForm.jsx";
import Navbar from "./components/Navbar.jsx";

function App() {
  return (
    <Router>
      <Navbar/>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/register" element={<RegisterForm />} />
         <Route path="/login" element={<LoginForm/>}/>
      </Routes>
    </Router>
  );
}

export default App;
