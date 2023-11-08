'use client'
import React, { useState, useEffect } from "react";

function useUser() {
  const userCookie = localStorage.getItem("user");
  const user = userCookie ? JSON.parse(userCookie) : null;
  return user;
}

const Cart = () => {
  const [cartItems, setCartItems] = useState([]);
  const [warning, setWarning] = useState("");
  const [stockData, setStockData] = useState([]);
  const user = useUser();
  const [userId, setUserId] = useState(user.userId);


  const fetchCart = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/v1/reg-user/carts/${parseInt(userId)}`);
      const data = await response.json();
      setCartItems(data);
      
    } catch (error) {
      console.error('Error fetching cart:', error);
    }
  };

  const updateCartItem = async (variantId, newCount) => {
    const obj = { variantId: variantId, newCount: newCount };

    try {
      const response = await fetch('/api/handledUpdateCount', {
        method: 'PUT',
        body: JSON.stringify({ "object": obj }),
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.ok) {
        const data = await response.json();
        console.log('Product updated successfully:', data);
        fetchCart();
      } else {
        console.error('Failed to update the product:', response.statusText);
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const handleRemoveItem = (variantId) => {
    updateCartItem(variantId, 0);
  };

  const handleIncreaseQuantity = (variantId) => {
    const updatedCart = cartItems.map((item) => {
      if (item.variantId === variantId) {
        return { ...item, quantity: item.quantity + 1 };
      }
      return item;
    });
    setCartItems(updatedCart);
    updateCartItem(
      variantId,
      updatedCart.find((item) => item.variantId === variantId).quantity
    );
  };

  const handleDecreaseQuantity = (variantId) => {
    const updatedCart = cartItems.map((item) => {
      if (item.variantId === variantId && item.quantity > 0) {
        return { ...item, quantity: item.quantity - 1 };
      }
      return item;
    });
    setCartItems(updatedCart);
    updateCartItem(
      variantId,
      updatedCart.find((item) => item.variantId === variantId).quantity
    );
  };

  const checkStock = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/v1/reg-user/carts/1/refresh");
      const data = await response.json();
      setStockData(data);
    } catch (error) {
      console.error('Error checking stock:', error);
    }
  };

  useEffect(() => {
    fetchCart();
  }, [userId]);

  useEffect(() => {
    checkStock();
  }, []);

  const isOutOfStock = (variantId) => {
    const cartItem = cartItems.find((item) => item.variantId === variantId);
    const stockItem = stockData.find((item) => item.variantId === variantId);
    if (cartItem && stockItem && cartItem.quantity > stockItem.quantity) {
      return true;
    }
    return false;
  };

  const handleCartCheckout = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/v1/reg-cust/orders/checkout/1');
      if (response.ok) {
        const data = await response.json();
        const orderId = data;

        window.location.href = `/cart-checkout?id=${orderId}`;
      } else {
        console.error('Failed to perform cart checkout:', response.statusText);
      }
    } catch (error) {
      console.error('Error during cart checkout:', error);
    }
  };

  return (
    <div className="max-w-screen-xl mx-auto p-4">
      {warning && (
        <p className="bg-red-500 text-white p-2 mb-4">{warning}</p>
      )}
      <ul>
        {cartItems.map((item) => (
          <li
            key={item.variantId}
            className={`border border-gray-300 p-4 mb-4 bg-white flex items-center ${
              isOutOfStock(item.variantId) ? "bg-red-100" : ""
            }`}
          >
            <img
              src={item.mainImage}
              alt={item.productName}
              className="w-16 h-16 mr-4"
            />
            <div>
              <h2 className="text-lg font-bold text-black">
                {item.productName}
              </h2>
              <p>Price: Rs{item.variantPrice}</p>
              <div className="flex items-center">
                <button
                  onClick={() => handleDecreaseQuantity(item.variantId)}
                  className="bg-red-500 text-white p-2 rounded"
                >
                  -
                </button>
                <p className="px-2">{item.quantity}</p>
                <button
                  onClick={() => handleIncreaseQuantity(item.variantId)}
                  className="bg-green-500 text-white p-2 rounded"
                >
                  +
                </button>
              </div>
            </div>
            {isOutOfStock(item.variantId) && (
              <p className="text-red-600">Out of Stock</p>
            )}
            <button
              onClick={() => handleRemoveItem(item.variantId)}
              className="ml-auto bg-red-500 text-white p-2 rounded"
            >
              Remove
            </button>
          </li>
        ))}
      </ul>
      <button
        onClick={handleCartCheckout}
        className="bg-blue-500 text-white p-2 rounded mt-4"
      >
        Cart Checkout
      </button>
    </div>
  );
};

export default Cart;
