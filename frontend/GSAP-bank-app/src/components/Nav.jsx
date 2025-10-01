import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { MdOutlineMenu } from "react-icons/md";

function Nav() {
  const [scrollDirection, setScrollDirection] = useState('up');
  const [lastScrollY, setLastScrollY] = useState(0);
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      const currentScrollY = window.scrollY;
      if (currentScrollY > lastScrollY) {
        setScrollDirection('down');
      } else {
        setScrollDirection('up');
      }
      setLastScrollY(currentScrollY);
      setIsScrolled(currentScrollY > 0);
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, [lastScrollY]);

    return (
        <motion.div
      className={`w-full py-5 lg:px-16 px-5 flex justify-between items-center z-[999] fixed ${isScrolled ? 'backdrop-blur-sm bg-opacity-60 bg-gray-50' : ''} transition-colors duration-200`}
      initial={{ y: 0 }}
      animate={scrollDirection === 'down' ? { y: '-100%' } : { y: 0 }}
      style={{ color: isScrolled ? 'black' : 'inherit' }}
    >
            <div className="logo">
              <svg width="120" height="40" xmlns="http://www.w3.org/2000/svg">
                <text x="0" y="30" fontFamily="Arial, sans-serif" fontSize="28" fill="currentColor">
                  Oracle
                </text>
              </svg>
            </div>

            <div className="hidden lg:flex links gap-10">
                {["Services", "Our work", "About us", "Insights", "Contact us"].map((item, index) => (
                    <a key={index} className={`text-xl font-regular ${index === 4 && "ml-80"}`}>{item}</a>
                ))}
            </div>
            <div className="lg:hidden text-2xl">
                <MdOutlineMenu />
            </div>
        </motion.div>
    )
}

export default Nav