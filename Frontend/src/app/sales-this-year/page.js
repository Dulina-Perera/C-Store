'use client'
import { useEffect, useState } from "react";

const ThisYearSales = () => {
  const [salesData, setSalesData] = useState([]);

  useEffect(() => {
    // Define the API endpoint URL
    const apiUrl = "http://localhost:8080/api/v1/reports/sales/products/max/THIS_YEAR";

    // Fetch data from the API endpoint using the fetch API
    fetch(apiUrl)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        
        setSalesData(data);
      })
      .catch((error) => {
        // Handle any errors, e.g., display an error message
        console.error("Error fetching data:", error);
      });
  }, []);

  return (
    <div>
      <h1>This Year's Sales</h1>
      <table>
        <thead>
          <tr>
            <th>Product ID</th>
            <th>Product Name</th>
            <th>Main Image</th>
            <th>Sales</th>
          </tr>
        </thead>
        <tbody>
          {salesData.map((product) => (
            <tr key={product.productId}>
              <td>{product.productId}</td>
              <td>{product.productName}</td>
              <td>{product.mainImage}</td>
              <td>{product.sales}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ThisYearSales;
