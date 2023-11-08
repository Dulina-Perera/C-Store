'use client';
import Link from 'next/link';
import React, { useState, useEffect } from 'react';
import Head from 'next/head';
import Popup from '@/components/Dropdown/Dropdown';
import Menu from '@/components/Menu';

export default function Home() {
  const [fadeIn, setFadeIn] = useState(false);
  const [userId, setUserId] = useState(null);
  const [user, setUser] = useState(null);

  // Get the user ID from local storage
  localStorage

  useEffect(() => {
    const timer = setTimeout(() => {
      setFadeIn(true);
    }, 5000);

    return () => {
      clearTimeout(timer);
    };
  }, []);

  const fetchUser = async () => {
    try {
      console.log("User ID:", userId)
      const response = await fetch(`http://localhost:8080/api/v1/users/${userId}`);
      if (response.ok) {
        const userData = await response.json();
        setUser(userData);
        console.log('User data:', userData);

        // Save the user object in local storage
        localStorage.setItem('user', JSON.stringify(userData));
      } else {
        console.error('Error fetching user data:', response.status, response.statusText);
      }
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  useEffect(() => {
    const userId = JSON.parse(localStorage.getItem('userId'));
    setUserId(userId);
  
    console.log("User ID:", userId);
  
    if (userId) {
      fetchUser();
    } else {
      console.error('User ID not found in localStorage');
    }
  }, [userId]);
  

  return (
    <div className="relative">
      <div className="bg-black h-screen z-10"> {/* Set a higher z-index to the background */}
        <video
          autoPlay
          loop
          muted
          className="absolute top-4 left-0 object-cover w-full h-full z-0" // Set a lower z-index for the video
        >
          <source
            src="https://www.apple.com/105/media/us/ipad-pro/2022/08087f4b-7539-4b1e-ae8a-adc18f4242ae/anim/hero/large_2x.mp4"
            type="video/mp4"
          />
        </video>

        <div className="absolute top-10 left-0 right-0 p-4 text-white text-center z-0">
          <h1
            className={`text-4xl font-semibold mb-4 ${
              fadeIn
                ? 'opacity-100 transition-opacity duration-1500 ease-in-out'
                : 'opacity-0'
            }`}
          >
            Welcome to C E-commerce
          </h1>
        </div>

        <div className="absolute bottom-0 left-0 right-0 p-4 text-gray-300 text-center z-0">
          <p className="text-lg animate__animated animate__fadeInUp animate__delay-1s mb-4">
            Explore a world of great products and deals.
          </p>

          <Link href="/allproducts">
            <button className="bg-black hover-bg-red text-white font-semibold py-2 px-4 rounded-full mt-4 animate__animated animate__fadeInUp animate__delay-2s border border-black hover:border-red-700">
              Shop Now
            </button>
          </Link>
          <p className="text-lg animate__animated animate__fadeInUp animate__delay-1s">
            Our electronics and toys selling website offers an extensive range
            of high-quality products, including the latest gadgets and toys for
            all ages. With a commitment to exceptional customer service, we
            provide secure online shopping, fast delivery, and a diverse
            selection to cater to the diverse needs of tech enthusiasts and toy
            lovers alike. Explore our offerings and experience a seamless
            shopping journey with us.
          </p>
        </div>
      </div>

      {/* The rest of your component content remains unchanged */}
    </div>
  );
}
