import React from "react";
import { useForm } from "react-hook-form";
import axios from "axios";

const CustomerForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset
  } = useForm();

  const onSubmit = async (data) => {
    try {
      const response = await axios.post(
        "http://localhost:8081/customerservice/api/customers", // updated backend URL
        data
      );
      alert("Customer registered successfully!");
      reset();
    } catch (error) {
      console.error("Error:", error.response?.data || error.message);
      alert("Registration failed. Please check the input and try again.");
    }
  };

  return (
    <div style={{ maxWidth: "600px", margin: "auto" }}>
      <h2>Customer Registration</h2>
      <form onSubmit={handleSubmit(onSubmit)}>
        {/* Full Name */}
        <label>Full Name</label>
        <input {...register("fullName", { required: "Full name is required" })} />
        {errors.fullName && <p>{errors.fullName.message}</p>}

        {/* Email */}
        <label>Email</label>
        <input
          {...register("email", {
            required: "Email is required",
            pattern: { value: /^\S+@\S+$/i, message: "Invalid email format" }
          })}
        />
        {errors.email && <p>{errors.email.message}</p>}

        {/* Phone */}
        <label>Phone</label>
        <input
          {...register("phone", {
            required: "Phone is required",
            pattern: { value: /^\d{10}$/, message: "Phone must be 10 digits" }
          })}
        />
        {errors.phone && <p>{errors.phone.message}</p>}

        {/* DOB */}
        <label>Date of Birth</label>
        <input
          type="date"
          {...register("dob", { required: "Date of birth is required" })}
        />
        {errors.dob && <p>{errors.dob.message}</p>}

        {/* Address */}
        <label>Address</label>
        <textarea {...register("address", { required: "Address is required" })} />
        {errors.address && <p>{errors.address.message}</p>}

        {/* PAN */}
        <label>PAN</label>
        <input
          {...register("pan", {
            required: "PAN is required",
            pattern: {
              value: /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/,
              message: "Invalid PAN format"
            }
          })}
        />
        {errors.pan && <p>{errors.pan.message}</p>}

        {/* Aadhaar */}
        <label>Aadhaar</label>
        <input
          {...register("aadhaar", {
            required: "Aadhaar is required",
            pattern: { value: /^\d{12}$/, message: "Aadhaar must be 12 digits" }
          })}
        />
        {errors.aadhaar && <p>{errors.aadhaar.message}</p>}

        {/* Submit Button */}
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default CustomerForm;
