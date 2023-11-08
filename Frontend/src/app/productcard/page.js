"use client";
import React, { useState } from "react";
import Link from "next/link";
import Cookies from "js-cookie";


function useUser() {
  // Retrieve and parse the user object from the local storage
  const userCookie = localStorage.getItem('user');
  const user = userCookie ? JSON.parse(userCookie) : null;

  return user;
}

export const ProductCard = ({ product }) => {
  const user = useUser();

  return (
    <div className="bg-white p-6 shadow-md rounded">
      <Link href={{
        pathname: "/productview",
        query: {
          id: product.productId,
        },
      }
        
      }>
        <div className="mb-4 relative w-64 h-64">

          <img
            src={product.mainImage}
            alt={product.productName}
            className="w-full h-full object-cover rounded-lg transition-transform transform hover:scale-110"
          />
        </div>
      </Link>

      <h3 className="text-xl font-semibold">{product.productName}</h3>
      <p className="text-gray-600">Rs. {product.basePrice}</p>
      {/* {user && user.role === 'REG_CUST' && (
        <div className="flex justify-between items-center mt-2">
          <Link
            href={{
              pathname: "/checkout",
              query: {
                productID: product.productId,
              },
            }}
          >
            <button className="bg-black text-white py-2 px-4 hover:bg-blue-900 rounded-full">
              Buy Now
            </button>
          </Link>
          <img
            src="https://cdn-icons-png.flaticon.com/512/263/263142.png"
            alt="Cart Icon"
            className="w-6 h-6 cursor-pointer"
            onClick={() => alert("add to cart logic")}
          />
        </div>
      )}
      {user && user.role === 'ADMIN' && (
        <div className="flex justify-between items-center mt-2">
          <Link
            href={{
              pathname: "/edditelements",
              query: {
                productID: product.productId,
              },
            }}
          >
            <button className="bg-black text-white py-2 px-4 hover:bg-blue-900 rounded-full">
              Edit
            </button>
          </Link>
        </div>
      )} */}
    </div>
  );
};

export default ProductCard;
