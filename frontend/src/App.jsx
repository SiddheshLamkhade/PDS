import React, { useEffect } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from './context/AuthContext';

// Pages
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import CitizenDashboard from './pages/CitizenDashboard';
import DealerDashboard from './pages/DealerDashboard';
import AdminDashboard from './pages/AdminDashboard';
import NotFound from './pages/NotFound';

function App() {
  const { user, loading } = useAuth();

  if (loading) {
    return (
      <div className="loading-screen">
        <div className="loader"></div>
        <p>Loading e-Ration PDS...</p>
      </div>
    );
  }

  // Debug log
  useEffect(() => {
    console.log('App render - User state:', user);
  }, [user]);

  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={user ? <Navigate to={`/${(user.role || 'citizen').toLowerCase()}`} /> : <Login />} />
      <Route path="/register" element={user ? <Navigate to={`/${(user.role || 'citizen').toLowerCase()}`} /> : <Register />} />
      
      <Route 
        path="/citizen/*" 
        element={user && user.role === 'CITIZEN' ? <CitizenDashboard /> : <Navigate to="/login" />} 
      />
      <Route 
        path="/dealer/*" 
        element={user && user.role === 'DEALER' ? <DealerDashboard /> : <Navigate to="/login" />} 
      />
      <Route 
        path="/admin/*" 
        element={user && user.role === 'ADMIN' ? <AdminDashboard /> : <Navigate to="/login" />} 
      />
      
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}

export default App;
