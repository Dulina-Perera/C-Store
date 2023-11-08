import React from 'react';

const ordersData = [
  {
    id: 1,
    customerAddress: '123 Main St, City, Country',
    date: '2023-09-10',
    paymentMethod: 'Credit Card',
    deliveryMethod: 'Express',
    orderStatus: 'Shipped',
    totalAmount: 100,
    shippingCost: 10,
    items: [
      {
        itemID: '123',
        itemName: 'Product A',
        itemVariant: 'Variant X',
        price: 25,
        quantity: 3,
      },
      
    ],
  },
  {
    id: 2,
    customerAddress: '456 Elm St, Town, Country',
    date: '2023-09-12',
    paymentMethod: 'PayPal',
    deliveryMethod: 'Standard',
    orderStatus: 'Processing',
    totalAmount: 75,
    shippingCost: 5,
    items: [
      {
        itemID: '223',
        itemName: 'Product C',
        itemVariant: 'Variant Z',
        price: 15,
        quantity: 5,
      },
      {
        itemID: '143',
        itemName: 'Product D',
        itemVariant: 'Variant W',
        price: 10,
        quantity: 1,
      },
    ],
  },
];

const OrderList = () => {
  return (
    <div className="bg-gray-100 p-4">
      <title>orders</title>
      <h1 className="text-2xl font-bold mb-4">List of Orders</h1>
      <div className="overflow-x-auto">
        <table className="min-w-full">
          <thead>
            <tr>
              <th className="border p-2">Order ID</th>
              <th className="border p-2">Customer Address</th>
              <th className="border p-2">Date</th>
              <th className="border p-2">Payment Method</th>
              <th className="border p-2">Delivery Method</th>
              <th className="border p-2">Order Status</th>
              <th className="border p-2">Shipping Cost</th>
              <th className="border p-2">Total Amount</th>
              <th className="border p-2">Items</th>
            </tr>
          </thead>
          <tbody>
            {ordersData.map((order) => (
              <tr key={order.id}>
                <td className="border p-2">{order.id}</td>
                <td className="border p-2">{order.customerAddress}</td>
                <td className="border p-2">{order.date}</td>
                <td className="border p-2">{order.paymentMethod}</td>
                <td className="border p-2">{order.deliveryMethod}</td>
                <td className="border p-2">{order.orderStatus}</td>
                <td className="border p-2">Rs. {order.shippingCost}</td>
                <td className="border p-2">Rs. {order.totalAmount}</td>
                <td className="border p-2">
                  <table className="table-fixed w-full">
                    <thead>
                      <tr>
                        <th className="w-1/3 border p-2">Item Name</th>
                        <th className="w-1/3 border p-2">Item Variant</th>
                        <th className="w-1/6 border p-2">Price</th>
                        <th className="w-1/6 border p-2">Quantity</th>
                      </tr>
                    </thead>
                    <tbody>
                      {order.items.map((item, index) => (
                        <tr key={index}>
                          <td className="border p-2">{item.itemName}</td>
                          <td className="border p-2">{item.itemVariant}</td>
                          <td className="border p-2">${item.price}</td>
                          <td className="border p-2">{item.quantity}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default OrderList;
