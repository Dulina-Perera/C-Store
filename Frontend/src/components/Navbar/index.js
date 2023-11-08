'use client'
import React, { use, useState } from 'react';
import Link from 'next/link';
import Cookies from 'js-cookie';
import Dropdown from '../Dropdown/Dropdown';
import Menu from '../Menu';
import { useEffect } from 'react';

function useUser() {
  const user = localStorage.getItem('user');
  return user ? JSON.parse(user) : null;
}

const Navbar = () => {
  const [user, setUser] = useState(useUser()); 
  const [isMenuOpen, setMenuOpen] = useState(false); // Manage the visibility state of the Menu

  const handleLogout = () => {
    localStorage.removeItem('user');
    localStorage.removeItem('userId'); 
    window.location.href = '/';
  };
  

  const toggleMenu = () => {
    setMenuOpen(!isMenuOpen);
  };

   

  return (
    <nav className="bg-black py-4 fixed top-0 left-0 w-full z-50">
      <div className="flex justify-between items-center">
        <div>
          <img
            src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spiking_logo.svg/1280px-Spiking_logo.svg.png"
            style={{ width: '290px', height: '70px' }}
            alt="Logo"
          />
        </div>
        <div>
          <ul className="flex justify-center space-x-6 text-white">
            
            <li>
              <Link href="/allproducts">All Products</Link>
            </li>
            <li className="group relative">
              <span onClick={toggleMenu}>Categories</span> {/* Button to toggle the Menu */}
              <Menu isOpen={isMenuOpen} /> {/* Pass the visibility state as a prop */}
            </li>
            {user && user.role === 'USER' && (
              <>
                
              </>
            )}
            {user && user.role === 'ADMIN' && (
              <>
                <li>
                  <Link href="/reports">Reports</Link>
                </li>
                <li>
                  <Link href="/add-product">Add New Product</Link>
                </li>
                <li>
                  <Link href="/orders">Orders</Link>
                </li>
              </>
            )}
          </ul>
        </div>
        <ul className="flex space-x-7 text-white">
          {(user?.role === "ADMIN" || user?.role === "REG_CUST")  ?(
            <>
              <li>
                <Link href="/account">Account</Link>
              </li>
              <li>
                <Link href="/cart">Cart</Link>
              </li>
              <li>
                <button onClick={handleLogout}>Logout</button>
              </li>
            </>
          ) : (
            <li>
              <Link href="/login">
                <button className="nav-button">Login</button>
              </Link>
            </li>
          )}
        </ul>
      </div>
      <style jsx>{`
        .nav-button {
          background-color: white;
          color: gray;
          padding: 8px 16px;
          border: none;
          border-radius: 4px;
          cursor: pointer;
        }
        .nav-button:hover {
          background-color: white;
          color: black; /* Dark Blue on hover */
        }
      `}</style>
    </nav>
  );
};

export default Navbar;
