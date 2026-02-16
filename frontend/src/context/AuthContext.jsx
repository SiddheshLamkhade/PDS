import React, { createContext, useContext, useState, useEffect } from 'react';
import api from '../api/axios';

const AuthContext = createContext(null);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    try {
      const token = localStorage.getItem('authToken');
      const role = localStorage.getItem('userRole');
      const userData = localStorage.getItem('userData');
      
      if (token && role && userData) {
        const parsedData = JSON.parse(userData);
        // Validate that role exists and is valid
        if (parsedData && role && ['CITIZEN', 'DEALER', 'ADMIN'].includes(role)) {
          setUser({ ...parsedData, role });
        } else {
          // Clear invalid data
          localStorage.clear();
        }
      }
    } catch (error) {
      console.error('Error loading user data:', error);
      localStorage.clear();
    }
    setLoading(false);
  }, []);

  const login = async (username, password) => {
    try {
      const response = await api.post('/auth/login', { username, password });
      const loginData = response.data.data;
      
      // Extract user info from nested structure
      const user = loginData.user;
      const role = user?.role || 'CITIZEN'; // Default to CITIZEN if role is missing
      const profile = loginData.citizenProfile || loginData.dealerProfile || {};
      
      // Merge user data with profile
      const userData = {
        id: user.id,
        username: user.username,
        email: user.email,
        fullName: user.fullName,
        role: role,
        ...profile
      };
      
      // For now, use username as token until JWT is implemented
      const token = `Bearer_${user.username}_${Date.now()}`;
      
      localStorage.setItem('authToken', token);
      localStorage.setItem('userRole', role);
      localStorage.setItem('userData', JSON.stringify(userData));
      
      setUser(userData);
      
      console.log('Login successful:', { role, userData }); // Debug log
      
      return { success: true, role: role };
    } catch (error) {
      console.error('Login error:', error); // Debug log
      return { 
        success: false, 
        message: error.response?.data?.message || 'Login failed' 
      };
    }
  };

  const logout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userRole');
    localStorage.removeItem('userData');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
};
