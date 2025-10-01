import React from "react";
import { useForm } from "react-hook-form";
import axios from "axios";
import './AdminReviewForm.css';

const AdminReviewForm = () => {
  const { register, handleSubmit, formState: { errors }, reset } = useForm();

  const onSubmit = async (data) => {
    try {
      await axios.put(`http://localhost:9093/kycservice/api/kyc/${data.customerId}/review`, {
        kycStatus: data.kycStatus,
        adminRemark: data.adminRemark
      });
      alert("KYC status updated!");
      reset();
    } catch (error) {
      console.error("Review Failed:", error);
      alert("Review failed.");
    }
  };

  return (
    <div style={{ maxWidth: "600px", margin: "auto" }}>
      <h2>Admin KYC Review</h2>
      <form onSubmit={handleSubmit(onSubmit)}>
        <label>Customer ID</label>
        <input {...register("customerId", { required: true })} />
        {errors.customerId && <p>Customer ID is required</p>}

        <label>Status</label>
        <select {...register("kycStatus", { required: true })}>
          <option value="">Select</option>
          <option value="VERIFIED">Verified</option>
          <option value="REJECTED">Rejected</option>
        </select>
        {errors.kycStatus && <p>Status is required</p>}

        <label>Admin Remark (optional)</label>
        <input {...register("adminRemark")} />

        <button type="submit">Update Status</button>
      </form>
    </div>
  );
};

export default AdminReviewForm;
