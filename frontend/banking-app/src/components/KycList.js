import React, { useEffect, useState } from "react";
import axios from "axios";

const KycList = () => {
  const [kycList, setKycList] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:9093/kycservice/api/kyc")
      .then(res => setKycList(res.data))
      .catch(err => console.error("Error loading KYC list", err));
  }, []);

  return (
    <div style={{ padding: "20px" }}>
      <h2>All KYC Entries</h2>
      <table border="1" cellPadding="10" style={{ width: "100%", marginTop: "10px" }}>
        <thead>
          <tr>
            <th>Customer ID</th>
            <th>PAN</th>
            <th>Aadhaar</th>
            <th>Status</th>
            <th>Admin Remark</th>
          </tr>
        </thead>
        <tbody>
          {kycList.map((kyc, idx) => (
            <tr key={idx}>
              <td>{kyc.customerId}</td>
              <td>{kyc.panNumber}</td>
              <td>{kyc.aadhaarNumber}</td>
              <td>{kyc.kycStatus}</td>
              <td>{kyc.adminRemark || "N/A"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default KycList;
