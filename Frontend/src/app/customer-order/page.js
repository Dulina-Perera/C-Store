// components/CustomerOrder.js
'use client'
import { useEffect, useState } from 'react';
import axios from 'axios';

const CustomerOrder = () => {
  const [customerOrders, setCustomerOrders] = useState([]);

  useEffect(() => {
    async function fetchCustomerOrders() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/reg-cust/reports/customer-order/1');
        setCustomerOrders(response.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    }

    fetchCustomerOrders();
  }, []);

  const getVariantProperties = (variant) => {
    return Object.keys(variant.properties);
  };

  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">Customer Order Details</h1>
      {customerOrders.map((order, orderIndex) => (
        <div key={orderIndex} className="mb-8">
          <h2 className="text-xl font-bold">Order ID: {order.orderId}</h2>
          <table className="border-collapse border">
            <thead>
              <tr>
                <th className="border px-4 py-2">Status</th>
                <th className="border px-4 py-2">Date</th>
                <th className="border px-4 py-2">Total Payment</th>
                <th className="border px-4 py-2">Payment Method</th>
                <th className="border px-4 py-2">Delivery Method</th>
                <th className="border px-4 py-2">Shipping Address</th>
                <th className="border px-4 py-2">Variants Name</th>
                {getVariantProperties(order.variants[0]).map((property, propertyIndex) => (
                  <th key={propertyIndex} className="border px-4 py-2">
                    {property}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {order.variants.map((variant, variantIndex) => (
                <tr key={variantIndex}>
                  <td className="border px-4 py-2">{order.status}</td>
                  <td className="border px-4 py-2">{order.date}</td>
                  <td className="border px-4 py-2">{order.totalPayment}</td>
                  <td className="border px-4 py-2">{order.paymentMethod}</td>
                  <td className="border px-4 py-2">{order.deliveryMethod}</td>
                  <td className="border px-4 py-2">
                    {`${order.shippingAddress.streetNumber} ${order.shippingAddress.streetName}, ${order.shippingAddress.city}, ${order.shippingAddress.zipcode}`}
                  </td>
                  <td className="border px-4 py-2">{variant.productName}</td>
                  {getVariantProperties(variant).map((property, propertyIndex) => (
                    <td key={propertyIndex} className="border px-4 py-2">
                      {variant.properties[property]}
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ))}
    </div>
  );
};

export default CustomerOrder;
