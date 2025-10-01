import React, { useState } from "react";
import axios from "axios";
import './KycStatusChecker.css'
const KycStatusChecker = () => {
  const [customerId, setCustomerId] = useState("");
  const [status, setStatus] = useState(null);

  const fetchStatus = async () => {
    try {
      const res = await axios.get(`http://localhost:9093/kycservice/api/kyc/${customerId}/status`);
      setStatus(res.data);
    } catch (err) {
      alert("KYC status not found.");
      setStatus(null);
    }
  };

  return (
    <div style={{ maxWidth: "600px", margin: "auto" }}>
      <h2>Check KYC Status</h2>
      <input
        type="text"
        value={customerId}
        onChange={(e) => setCustomerId(e.target.value)}
        placeholder="Enter Customer ID"
      />
      <button onClick={fetchStatus}>Check</button>

      {status && (
        <div style={{ marginTop: "20px" }}>
          <p><strong>Status:</strong> {status.kycStatus}</p>
          <p><strong>Remark:</strong> {status.adminRemark}</p>
        </div>
      )}
    </div>
  );
};

export default KycStatusChecker;
