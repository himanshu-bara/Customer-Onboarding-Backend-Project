// src/App.jsx

import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import CustomerForm from "./components/CustomerForm";
import KycForm from "./components/KycForm";
import AdminReviewForm from "./components/AdminReviewForm";
import KycStatusChecker from "./components/KycStatusChecker";
import KycList from "./components/KycList";
import ReviewKyc from "./components/ReviewKyc";
import AccountService from "./components/AccountService";
import './App.css';
const App = () => {
  return (
    <Router>
      <div className="app-container">
        <h1>Bank Admin Dashboard</h1>

        <nav className="navbar">
          <Link to="/">Dashboard</Link>
          <Link to="/customer">Register Customer</Link>
          <Link to="/kyc">Submit KYC</Link>
          <Link to="/kyc-list">View KYC List</Link>
          <Link to="/kyc-status">KYC Status</Link>
          <Link to="/admin-review">Admin Review</Link>
          <Link to="/review-kyc">Review KYC Docs</Link>
          <Link to="/account">Account Services</Link> 
        </nav>

        <div className="container">
          <Routes>
            <Route path="/" element={<h2>Welcome to the Bank Admin Dashboard</h2>} />
            <Route path="/customer" element={<CustomerForm />} />
            <Route path="/kyc" element={<KycForm />} />
            <Route path="/kyc-list" element={<KycList />} />
            <Route path="/kyc-status" element={<KycStatusChecker />} />
            <Route path="/admin-review" element={<AdminReviewForm />} />
            <Route path="/review-kyc" element={<ReviewKyc />} />
            <Route path="/account" element={<AccountService />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
};

export default App;
