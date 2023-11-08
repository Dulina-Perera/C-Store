'use client'
import React, { useState } from 'react';
import Cookies from 'js-cookie';
import Link from 'next/link';


const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = {
      email: email,
      password: password
    };

    try {
      const response = await fetch('/api/handledLogin', {
        method: 'POST',
        body: JSON.stringify(formData),
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.ok) {
        const data = await response.json();
        console.log('Login successful:', data);

        // Assuming that your API response contains a 'token' field
        const token = data;

    
        localStorage.setItem('userId', token.userId);

        
        

        

        // Redirect to the home page or another appropriate page
        window.location.href = '/';
      } else {
        console.error('Login Error:', response.statusText);
        setError('Login failed. Please check your credentials.');
      }
    } catch (error) {
      console.error('Login Error:', error);
      setError('An error occurred during login.');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-black">
      <div className="bg-white p-8 rounded shadow-md w-96">
        <h2 className="text-2xl font-semibold mb-4">Login</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="email">
              Email
            </label>
            <input
              className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              id="email"
              type="email"
              placeholder="Email"
              value={email}
              onChange={handleEmailChange}
              required
            />
          </div>

          <div className="mb-6">
            <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="password">
              Password
            </label>
            <input
              className="appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
              id="password"
              type="password"
              placeholder="Password"
              value={password}
              onChange={handlePasswordChange}
              required
            />
          </div>

          {error && <div className="text-red-500">{error}</div>}

          <div className="flex items-center justify-between">
            <button
              className="bg-black hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
              type="submit"
            >
              Sign In
            </button>
            <Link href="/register">
              <button className="text-blue-500 hover:underline">Sign Up</button>
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;
