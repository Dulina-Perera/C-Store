'use client'
import React, { useState, useEffect, use } from 'react';
function useUser() {
  // Retrieve and parse the user object from the local storage
  const userCookie = localStorage.getItem("user");
  const user = userCookie ? JSON.parse(userCookie) : null;

  return user;
}


const CheckoutContainer = ({ searchParams }) => {
  const productID = searchParams.id;
  const totalPrice = searchParams.totalPrice;
  const properties = searchParams.selectedPropertiesList;
  const user = useUser();
  const userId = user.userId;
  

  const [customer, setCustomer] = useState({
    firstName: '',
    lastName: '',
    email: '',
    telephoneNumbers: [''],
    addresses: [{ streetNumber: '', streetName: '', city: '', zipcode: '' }],
  });

  const [currentDateTime, setCurrentDateTime] = useState('');
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState('VISA');
  const [selectedDeliveryMethod, setSelectedDeliveryMethod] = useState('Express Delivery');

  const [product, setProduct] = useState(null);

  useEffect(() => {
    if (productID) {
      // Fetch the product details from your API
      fetch(`http://localhost:8080/api/v1/products/select/${productID}`)
        .then((response) => response.json())
        .then((data) => {
          setProduct(data);
        })
        .catch((error) => {
          console.error(error);
        });

        
    }

  }, [productID]);


  
  

  

  const handleCancelCheckout = () => {
    alert('Checkout canceled');
    window.location.href = '/allproducts';
  };

  const handleSubmit = async() => {
    // Create the order object based on state and selected methods
    const orderObject = {
      date: currentDateTime,
      totalPayment: parseFloat(totalPrice),
      paymentMethod: selectedPaymentMethod,
      deliveryMethod: selectedDeliveryMethod,
      email: customer.email,
      streetNumber: customer.addresses[0].streetNumber,
      streetName: customer.addresses[0].streetName,
      city: customer.addresses[0].city,
      zipcode: parseInt(customer.addresses[0].zipcode),
      telephoneNumber: customer.telephoneNumbers[0],
    };

    console.log("Order Object:", orderObject);



    try {
      const response = await fetch('/api/handleOrderSubmit', {
        method: 'POST', 
        body: JSON.stringify({
            "categoryList" : orderObject ,
            "productID" :   productID,
    }), 
        headers: {
          'Content-Type': 'application/json',
        },
      });
      
  
      if (response.ok) {
        console.log("done" );
      } else {
        console.error('Failed to add the product:', response.statusText);
      }
    } catch (error) {
      console.error('Error while adding the product:', error);
    }


    // Log the order object to the console
    console.log(orderObject);

    

    // You can also send the order object to your server for further processing, if needed.
  };

  useEffect(() => {
    // Update current date and time
    const intervalId = setInterval(() => {
      const currentDateTime = new Date().toLocaleString('en-US', {
        timeZone: 'Asia/Colombo',
        timeZoneName: 'short',
      });
      setCurrentDateTime(currentDateTime);
    }, 1000);

    return () => {
      // Clear the interval when the component unmounts
      clearInterval(intervalId);
    };
  }, []);

  return (
    <div className="flex justify-center items-center bg-gray-900">
      <div className="p-4 text-left bg-gray-700 w-full max-w-xl rounded-lg shadow-lg">
        <h1 className="text-3xl font-bold mb-4 text-yellow-400 text-center">Checkout Page</h1>
        {product && (
          <div className="mb-4">
            <h2 className="text-2xl font-semibold text-yellow-400">Product Details</h2>
            <div className="mb-4">
              <div className="relative w-64 h-64 mx-auto">
                <div className="absolute inset-0 border-2 border-black rounded-lg"></div>
                <img
                  src={product.imageUrl}
                  alt={product.productName}
                  className="w-full h-full object-cover rounded-lg"
                />
              </div>
              <p className="mt-2 text-center text-yellow-400">Name: {product.productName}</p>
              <p className="text-center text-yellow-400">Description: {product.description}</p>
              <p className="text-center text-yellow-400">Price: Rs.{totalPrice}</p>
            </div>
          </div>
        )}
        <form>
          <div className="mb-4">
            <h2 className="text-2xl font-semibold text-yellow-400">User Details</h2>
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label htmlFor="firstName" className="block font-semibold text-yellow-400">
                  First Name:
                </label>
                <input
                  type="text"
                  id="firstName"
                  name="firstName"
                  className="w-full px-4 py-2 rounded border"
                  value={customer.firstName}
                  onChange={(e) => setCustomer({ ...customer, firstName: e.target.value })}
                />
              </div>
              <div>
                <label htmlFor="lastName" className="block font-semibold text-yellow-400">
                  Last Name:
                </label>
                <input
                  type="text"
                  id="lastName"
                  name="lastName"
                  className="w-full px-4 py-2 rounded border"
                  value={customer.lastName}
                  onChange={(e) => setCustomer({ ...customer, lastName: e.target.value })}
                />
              </div>
              <div>
                <label htmlFor="email" className="block font-semibold text-yellow-400">
                  Email:
                </label>
                <input
                  type="text"
                  id="email"
                  name="email"
                  className="w-full px-4 py-2 rounded border"
                  value={customer.email}
                  onChange={(e) => setCustomer({ ...customer, email: e.target.value })}
                />
              </div>
              <div>
                <label htmlFor="telephoneNumbers" className="block font-semibold text-yellow-400">
                  Phone:
                </label>
                <input
                  type="text"
                  id="telephoneNumbers"
                  name="telephoneNumbers[0]"
                  className="w-full px-4 py-2 rounded border"
                  value={customer.telephoneNumbers[0]}
                  onChange={(e) =>
                    setCustomer({
                      ...customer,
                      telephoneNumbers: [e.target.value],
                    })
                  }
                />
              </div>
              <div>
                <label htmlFor="addresses[0].streetNumber" className="block font-semibold text-yellow-400">
                  Street Number:
                </label>
                <input
                  type="text"
                  id="addresses[0].streetNumber"
                  name="addresses[0].streetNumber"
                  className="w-full px-4 py-2 rounded border"
                  value={customer.addresses[0].streetNumber}
                  onChange={(e) =>
                    setCustomer({
                      ...customer,
                      addresses: [{ ...customer.addresses[0], streetNumber: e.target.value }],
                    })
                  }
                />
              </div>
              <div>
                <label htmlFor="addresses[0].streetName" className="block font-semibold text-yellow-400">
                  Street Name:
                </label>
                <input
                  type="text"
                  id="addresses[0].streetName"
                  name="addresses[0].streetName"
                  className="w-full px-4 py-2 rounded border"
                  value={customer.addresses[0].streetName}
                  onChange={(e) =>
                    setCustomer({
                      ...customer,
                      addresses: [{ ...customer.addresses[0], streetName: e.target.value }],
                    })
                  }
                />
              </div>
              <div>
                <label htmlFor="addresses[0].city" className="block font-semibold text-yellow-400">
                  City:
                </label>
                <input
                  type="text"
                  id="addresses[0].city"
                  name="addresses[0].city"
                  className="w-full px-4 py-2 rounded border"
                  value={customer.addresses[0].city}
                  onChange={(e) =>
                    setCustomer({
                      ...customer,
                      addresses: [{ ...customer.addresses[0], city: e.target.value }],
                    })
                  }
                />
              </div>
              <div>
                <label htmlFor="addresses[0].zipcode" className="block font-semibold text-yellow-400">
                  Zipcode:
                </label>
                <input
                  type="text"
                  id="addresses[0].zipcode"
                  name="addresses[0].zipcode"
                  className="w-full px-4 py-2 rounded border"
                  value={customer.addresses[0].zipcode}
                  onChange={(e) =>
                    setCustomer({
                      ...customer,
                      addresses: [{ ...customer.addresses[0], zipcode: e.target.value }],
                    })
                  }
                />
              </div>
            </div>
          </div>
          <div className="mb-4">
            <h2 className="text-2xl font-semibold text-yellow-400">Payment Method</h2>
            <div>
              <label className="block font-semibold text-yellow-400">Payment Method:</label>
              <select
                name="paymentMethod"
                className="w-full px-4 py-2 rounded border"
                onChange={(e) => setSelectedPaymentMethod(e.target.value)}
              >
                <option value="VISA">VISA</option>
                <option value="MasterCard">MasterCard</option>
                <option value="PayPal">PayPal</option>
              </select>
            </div>
          </div>
          <div className="mb-4">
            <h2 className="text-2xl font-semibold text-yellow-400">Delivery Method</h2>
            <div>
              <label className="block font-semibold text-yellow-400">Delivery Method:</label>
              <select
                name="deliveryMethod"
                className="w-full px-4 py-2 rounded border"
                onChange={(e) => setSelectedDeliveryMethod(e.target.value)}
              >
                <option value="expressShip">Express Delivery</option>
                <option value="dex">Dex</option>
              </select>
            </div>
          </div>
          <div className="text-center">
            <button type="button" onClick={handleSubmit} className="bg-yellow-400 text-gray-900 px-4 py-2 rounded">
              Submit
            </button>
          </div>
        </form>
        <p className="text-yellow-400 mt-4 text-center">Current Date and Time: {currentDateTime}</p>
        <button onClick={handleCancelCheckout} className="block mt-4 mx-auto bg-red-500 text-white px-4 py-2 rounded">
          Cancel
        </button>
      </div>
    </div>
  );
};

export default CheckoutContainer;
