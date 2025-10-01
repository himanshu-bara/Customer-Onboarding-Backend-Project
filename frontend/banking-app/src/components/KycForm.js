import React from "react";
import { useForm } from "react-hook-form";
import axios from "axios";
import './KycForm.css';

const KycForm = () => {
  const { register, handleSubmit, formState: { errors }, reset } = useForm();

  const onSubmit = async (data) => {
    try {
      const file = data.photograph[0];
      const reader = new FileReader();

      reader.onloadend = async () => {
        const base64Image = reader.result.split(",")[1];

        const payload = {
          customerId: data.customerId,
          panNumber: data.panNumber,       // ✅ corrected
          aadhaarNumber: data.aadhaarNumber, // ✅ corrected
          photograph: base64Image
        };

        const response = await axios.post("http://localhost:9093/kycservice/api/kyc", payload);
        alert("KYC submitted successfully!");
        reset();
      };

      reader.readAsDataURL(file);
    } catch (error) {
      console.error("KYC Submission Failed:", error);
      alert("KYC submission failed. Check inputs.");
    }
  };

  return (
    <div style={{ maxWidth: "600px", margin: "auto" }}>
      <h2>KYC Submission</h2>
      <form onSubmit={handleSubmit(onSubmit)}>
        {/* Customer ID */}
        <label>Customer ID</label>
        <input {...register("customerId", { required: "Customer ID is required" })} />
        {errors.customerId && <p>{errors.customerId.message}</p>}

        {/* PAN */}
        <label>PAN</label>
        <input
          {...register("panNumber", {
            required: "PAN is required",
            pattern: {
              value: /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/,
              message: "Invalid PAN format"
            }
          })}
        />
        {errors.panNumber && <p>{errors.panNumber.message}</p>} {/* ✅ fixed */}

        {/* Aadhaar */}
        <label>Aadhaar</label>
        <input
          {...register("aadhaarNumber", {
            required: "Aadhaar is required",
            pattern: { value: /^\d{12}$/, message: "Aadhaar must be 12 digits" }
          })}
        />
        {errors.aadhaarNumber && <p>{errors.aadhaarNumber.message}</p>} {/* ✅ fixed */}

        {/* Photograph */}
        <label>Photograph</label>
        <input
          type="file"
          accept="image/*"
          {...register("photograph", { required: "Photograph is required" })}
        />
        {errors.photograph && <p>{errors.photograph.message}</p>}

        {/* Submit */}
        <button type="submit">Submit KYC</button>
      </form>
    </div>
  );
};

export default KycForm;
