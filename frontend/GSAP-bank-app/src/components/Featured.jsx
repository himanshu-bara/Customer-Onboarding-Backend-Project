// import React, { useState } from 'react'
// import { motion } from 'framer-motion';

// function Featured() {
//     let data = [
//         {title:"Customer",image:"bg-Feature1",subArr:["REGISTER","COPYWRITING","SALES DECK","SLIDES DESIGN"]},
//         {title:"Admin",image:"bg-Feature2",subArr:["AGENCY","COMPANY PRESENTATION"]},
//         {title:"PREMIUM BLEND",image:"bg-Feature3",subArr:["BRANDED TEMPLATE"]},
//         {title:"TRAWA",image:"bg-Feature4",subArr:["BRAND IDENTITY","DESIGN RESEARCH","INVESTOR DECK"]},
//     ]

//     const [hoveredIndex, setHoveredIndex] = useState(null);

//     const handleMouseEnter = (index) => {
//         setHoveredIndex(index);
//     };

//     const handleMouseLeave = () => {
//         setHoveredIndex(null);
//     };

//     return (
//         <div className='w-full pt-16 md:px-16 px-5 md:mb-0 mb-40'>
//             <h1 className='md:text-[3vw] text-[7vw] font-semibold tracking-tight'>Featured projects</h1>
//             <div className='w-full border-t-[2px] my-10'></div>
//             <div className='w-full grid md:grid-cols-2 gap-5'>

//                 {data.map((card, no) => (
//                     <div 
//                         onMouseEnter={() => handleMouseEnter(no)} 
//                         onMouseLeave={() => handleMouseLeave()} 
//                         key={no} 
//                         className='w-full md:h-[90vh] h-[40vh]'
//                     >
//                         <h3 className='flex items-center gap-1 text-lg font-light mb-5'>
//                             <div className='size-2 bg-black rounded-full'></div>{card.title}
//                         </h3>
//                         <div className='w-[100%] md:w-full h-[60%] md:h-[80%] relative'>
//                             <h2 className={`hidden absolute overflow-hidden md:flex top-1/2 ${no % 2 === 0 ? "left-full -translate-x-[50%]" : "right-full translate-x-[50%]"} -translate-y-[50%] z-10 text-[6vw] font-bold text-lime-400 tracking-tight`}>
//                                 {card.title.split('').map((alphab, index) => (
//                                     <motion.span 
//                                         initial={{ y: "100%" }} 
//                                         animate={hoveredIndex === no ? { y: "0" } : { y: "100%" }} 
//                                         transition={{ ease: [0.22, 1, 0.36, 1], delay: index * 0.06 }} 
//                                         className='inline-block' 
//                                         key={index}
//                                     >
//                                         {alphab}
//                                     </motion.span>
//                                 ))}
//                             </h2>
//                             <div className={`scale-[100%] rounded-xl w-full h-full ${card.image} bg-cover bg-center`}></div>
//                         </div>
//                         <div className='flex gap-3 mt-5'>
//                             {card.subArr.map((tag, index) => (
//                                 <button 
//                                     key={index} 
//                                     className='px-2 py-1 md:text-base text-[0.6rem] rounded-full border-2 border-gray-700 hover:bg-black hover:text-white transition duration-700'
//                                 >
//                                     {tag}
//                                 </button>
//                             ))}
//                         </div>
//                     </div>
//                 ))}
//             </div>
//             <div className='grid place-items-center mb-5'>
//                 <div className='px-5 py-3 rounded-full flex items-center justify-center bg-black text-white group'>  
//                     <button className='font-semibold w-fit mr-2'>VIEW ALL CASE STUDIES</button>
//                 </div>
//             </div>
//         </div>
//     )
// }

// export default Featured;

import React, { useState, useRef, useEffect } from 'react';
import { motion } from 'framer-motion';
import { gsap } from 'gsap';
import { useForm } from "react-hook-form";
import axios from "axios";

function Featured() {
    const data = [
        { title: "Customer", image: "bg-Feature1", subArr: ["REGISTER", "COPYWRITING"] },
        { title: "Admin", image: "bg-Feature2", subArr: ["AGENCY", "COMPANY PRESENTATION"] },
        { title: "PREMIUM BLEND", image: "bg-Feature3", subArr: ["BRANDED TEMPLATE"] },
        { title: "TRAWA", image: "bg-Feature4", subArr: ["BRAND IDENTITY"] },
    ];

    const [hoveredIndex, setHoveredIndex] = useState(null);
    const [modalType, setModalType] = useState(null);
    const modalRef = useRef(null);

    const openModal = (type) => setModalType(type);
    const closeModal = () => {
        gsap.to(modalRef.current, {
            opacity: 0,
            y: 50,
            duration: 0.5,
            onComplete: () => setModalType(null)
        });
    };

    useEffect(() => {
        if (modalType) {
            gsap.fromTo(modalRef.current,
                { opacity: 0, y: 50 },
                { opacity: 1, y: 0, duration: 0.5 }
            );
        }
    }, [modalType]);

    // --- CustomerForm inside Featured ---
    const {
        register,
        handleSubmit,
        formState: { errors },
        reset
    } = useForm();

    const onSubmit = async (data) => {
        try {
            const response = await axios.post(
                "http://localhost:8081/customerservice/api/customers",
                data
            );
            alert("Customer registered successfully!");
            reset();
            closeModal();
        } catch (error) {
            console.error("Error:", error.response?.data || error.message);
            alert("Registration failed. Please check the input and try again.");
        }
    };

    return (
        <div className='w-full pt-16 md:px-16 px-5 md:mb-0 mb-40 relative'>
            <h1 className='md:text-[3vw] text-[7vw] font-semibold tracking-tight'>Featured modules</h1>
            <div className='w-full border-t-[2px] my-10'></div>
            <div className='w-full grid md:grid-cols-2 gap-5'>
                {data.map((card, no) => (
                    <div
                        onMouseEnter={() => setHoveredIndex(no)}
                        onMouseLeave={() => setHoveredIndex(null)}
                        onClick={() => (card.title === "Customer" || card.title === "Admin") && openModal(card.title)}
                        key={no}
                        className='cursor-pointer w-full md:h-[90vh] h-[40vh]'
                    >
                        <h3 className='flex items-center gap-1 text-lg font-light mb-5'>
                            <div className='size-2 bg-black rounded-full'></div>{card.title}
                        </h3>
                        <div className='w-[100%] md:w-full h-[60%] md:h-[80%] relative'>
                            <h2 className={`hidden absolute overflow-hidden md:flex top-1/2 ${no % 2 === 0 ? "left-full -translate-x-[50%]" : "right-full translate-x-[50%]"} -translate-y-[50%] z-10 text-[6vw] font-bold text-lime-400 tracking-tight`}>
                                {card.title.split('').map((alphab, index) => (
                                    <motion.span
                                        initial={{ y: "100%" }}
                                        animate={hoveredIndex === no ? { y: "0" } : { y: "100%" }}
                                        transition={{ ease: [0.22, 1, 0.36, 1], delay: index * 0.06 }}
                                        className='inline-block'
                                        key={index}
                                    >
                                        {alphab}
                                    </motion.span>
                                ))}
                            </h2>
                            <div className={`scale-[100%] rounded-xl w-full h-full ${card.image} bg-cover bg-center`}></div>
                        </div>
                        <div className='flex gap-3 mt-5'>
                            {card.subArr.map((tag, index) => (
                                <button
                                    key={index}
                                    className='px-2 py-1 md:text-base text-[0.6rem] rounded-full border-2 border-gray-700 hover:bg-black hover:text-white transition duration-700'
                                >
                                    {tag}
                                </button>
                            ))}
                        </div>
                    </div>
                ))}
            </div>

            {/* Modal Section */}
            {modalType && (
                <div className='fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 backdrop-blur-sm flex items-center justify-center z-50'>
                    <div ref={modalRef} className='bg-white p-10 rounded-xl w-[90%] md:w-[40%] max-h-[90vh] overflow-y-auto relative'>
                        <button className='absolute top-3 right-4 text-black text-xl' onClick={closeModal}>×</button>

                        {modalType === "Customer" && (
                            <>
                                <h2 className='text-2xl font-bold mb-4'>Customer Registration</h2>
                                <form onSubmit={handleSubmit(onSubmit)} className='flex flex-col gap-4'>
                                    <input placeholder='Full Name' {...register("fullName", { required: "Full name is required" })} className='border p-2 rounded-md' />
                                    {errors.fullName && <p className="text-red-500 text-sm">{errors.fullName.message}</p>}

                                    <input placeholder='Email' {...register("email", {
                                        required: "Email is required",
                                        pattern: { value: /^\S+@\S+$/i, message: "Invalid email format" }
                                    })} className='border p-2 rounded-md' />
                                    {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}

                                    <input placeholder='Phone' {...register("phone", {
                                        required: "Phone is required",
                                        pattern: { value: /^\d{10}$/, message: "Phone must be 10 digits" }
                                    })} className='border p-2 rounded-md' />
                                    {errors.phone && <p className="text-red-500 text-sm">{errors.phone.message}</p>}

                                    <input type="date" {...register("dob", { required: "Date of birth is required" })} className='border p-2 rounded-md' />
                                    {errors.dob && <p className="text-red-500 text-sm">{errors.dob.message}</p>}

                                    <textarea placeholder='Address' {...register("address", { required: "Address is required" })} className='border p-2 rounded-md' />
                                    {errors.address && <p className="text-red-500 text-sm">{errors.address.message}</p>}

                                    <input placeholder='PAN' {...register("pan", {
                                        required: "PAN is required",
                                        pattern: {
                                            value: /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/,
                                            message: "Invalid PAN format"
                                        }
                                    })} className='border p-2 rounded-md' />
                                    {errors.pan && <p className="text-red-500 text-sm">{errors.pan.message}</p>}

                                    <input placeholder='Aadhaar' {...register("aadhaar", {
                                        required: "Aadhaar is required",
                                        pattern: { value: /^\d{12}$/, message: "Aadhaar must be 12 digits" }
                                    })} className='border p-2 rounded-md' />
                                    {errors.aadhaar && <p className="text-red-500 text-sm">{errors.aadhaar.message}</p>}

                                    <button type="submit" className='bg-black text-white py-2 rounded-md hover:bg-gray-800'>Register</button>
                                </form>
                            </>
                        )}

                        {modalType === "Admin" && (
                            <h2 className='text-2xl font-bold'>Admin Module Coming Soon...</h2>
                        )}
                    </div>
                </div>
            )}
        </div>
    );
}

export default Featured;

