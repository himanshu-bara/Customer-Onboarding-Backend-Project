import React, { useState } from 'react';
import axios from 'axios';
import './AccountService.css';

function AccountService() {
  const [customerId, setCustomerId] = useState('');
  const [account, setAccount] = useState(null);
  const [message, setMessage] = useState('');

  const [formData, setFormData] = useState({
    customerId: '',
    panNumber: '',
    aadhaarNumber: ''
  });

  const validateInputs = () => {
    const panRegex = /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/;
    const aadhaarRegex = /^\d{12}$/;

    if (!formData.customerId || !formData.panNumber || !formData.aadhaarNumber) {
      setMessage('❌ All fields are required.');
      return false;
    }

    if (!panRegex.test(formData.panNumber)) {
      setMessage('❌ Invalid PAN number format.');
      return false;
    }

    if (!aadhaarRegex.test(formData.aadhaarNumber)) {
      setMessage('❌ Aadhaar number must be exactly 12 digits.');
      return false;
    }

    return true;
  };

  const handleCreateAccount = async () => {
    if (!validateInputs()) return;

    try {
      const response = await axios.post('http://localhost:8083/account-api/account', formData);
      setAccount(response.data);
      setMessage('✅ Account created successfully!');
    } catch (error) {
      setMessage('❌ Failed to create account. Check if KYC is verified.');
    }
  };

  const handleGetAccount = async () => {
    try {
      const response = await axios.get(`http://localhost:8083/account-api/account/customer`, {
        params: { customerId }
      });
      setAccount(response.data);
      setMessage('✅ Account fetched successfully!');
    } catch (error) {
      setMessage('❌ No account found for this customer ID.');
    }
  };

  return (
    <div className="account-container">
      <h2>Account Service</h2>

      <div className="form-section">
        <h3>Create Account</h3>
        <input
          type="text"
          placeholder="Customer ID"
          value={formData.customerId}
          onChange={(e) => setFormData({ ...formData, customerId: e.target.value })}
        />
        <input
          type="text"
          placeholder="PAN Number (ABCDE1234F)"
          value={formData.panNumber}
          onChange={(e) => setFormData({ ...formData, panNumber: e.target.value })}
        />
        <input
          type="text"
          placeholder="Aadhaar Number (12 digits)"
          value={formData.aadhaarNumber}
          onChange={(e) => setFormData({ ...formData, aadhaarNumber: e.target.value })}
        />
        <button onClick={handleCreateAccount}>Create Account</button>
      </div>

      <div className="form-section">
        <h3>Get Account by Customer ID</h3>
        <input
          type="text"
          placeholder="Enter Customer ID"
          value={customerId}
          onChange={(e) => setCustomerId(e.target.value)}
        />
        <button onClick={handleGetAccount}>Fetch Account</button>
      </div>

      {message && <p className="message">{message}</p>}

      {account && (
        <div className="account-details">
          <h4>Account Details:</h4>
          <p><strong>Account Number:</strong> {account.accountId}</p>
          <p><strong>Customer ID:</strong> {account.customerId}</p>
        </div>
      )}
    </div>
  );
}

export default AccountService;
