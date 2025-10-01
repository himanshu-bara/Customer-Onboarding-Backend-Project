// src/components/ReviewKycPage.jsx

import React, { useEffect, useState } from "react";
import axios from "axios";
import "./ReviewKyc.css";

const ReviewKycPage = () => {
  const [kycList, setKycList] = useState([]);
  const [statusUpdates, setStatusUpdates] = useState({}); // per customerId

  useEffect(() => {
    axios
      .get("http://localhost:9093/kycservice/api/kyc")
      .then((res) => setKycList(res.data))
      .catch((err) => console.error("Error fetching KYC list", err));
  }, []);

  const handleStatusChange = (customerId, field, value) => {
    setStatusUpdates((prev) => ({
      ...prev,
      [customerId]: {
        ...prev[customerId],
        [field]: value,
      },
    }));
  };

  const submitReview = (customerId) => {
    const { kycStatus, adminRemark } = statusUpdates[customerId] || {};

    if (!kycStatus) {
      alert("Please select status");
      return;
    }

    axios
      .put(`http://localhost:9093/kycservice/api/kyc/${customerId}/review`, {
        kycStatus,
        adminRemark,
      })
      .then(() => {
        alert("Status updated");
        // Optionally refresh the list
      })
      .catch((err) => {
        console.error("Failed to update status", err);
        alert("Update failed");
      });
  };

  return (
    <div className="review-container">
      <h2>KYC Review Page</h2>
      {kycList.length === 0 ? (
        <p>No KYC entries available.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Customer ID</th>
              <th>PAN</th>
              <th>Aadhaar</th>
              <th>Photograph</th>
              <th>Status</th>
              <th>Remark</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {kycList.map((kyc) => (
              <tr key={kyc.customerId}>
                <td>{kyc.customerId}</td>
                <td>{kyc.panNumber}</td>
                <td>{kyc.aadhaarNumber}</td>
                <td>
                  <img
                    src={`http://localhost:9093/kycservice/api/kyc/${kyc.customerId}/photo/view`}
                    alt="KYC"
                    className="kyc-photo"
                  />
                </td>
                <td>
                  <select
                    value={statusUpdates[kyc.customerId]?.kycStatus || ""}
                    onChange={(e) =>
                      handleStatusChange(kyc.customerId, "kycStatus", e.target.value)
                    }
                  >
                    <option value="">Select</option>
                    <option value="VERIFIED">Verify</option>
                    <option value="REJECTED">Reject</option>
                  </select>
                </td>
                <td>
                  <input
                    type="text"
                    placeholder="Remark"
                    value={statusUpdates[kyc.customerId]?.adminRemark || ""}
                    onChange={(e) =>
                      handleStatusChange(kyc.customerId, "adminRemark", e.target.value)
                    }
                  />
                </td>
                <td>
                  <button onClick={() => submitReview(kyc.customerId)}>Submit</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default ReviewKycPage;
