'use client'
import React, { useEffect, useState } from 'react';

const ReportOne = () => {
  const [reportData, setReportData] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/v1/all/reports/products/sales/max/THIS_YEAR/2')
      .then((response) => response.json())
      .then((data) => setReportData(data))
      .catch((error) => console.error(error));
  }, []);

  return (
    <div className="bg-gray-900 h-screen flex items-center justify-center">
      <div className="flex flex-wrap">
        {reportData.map((report) => (
          <div key={report.productId} className="m-4">
            <div className="bg-yellow-400 p-4 rounded-lg shadow-md">
              <img
                src={report.mainImage}
                alt={report.productName}
                className="w-32 h-32 object-cover mx-auto"
              />
              <h2 className="text-lg text-gray-900 font-semibold mt-2">
                {report.productName}
              </h2>
              <p className="text-gray-900 mt-2">Sales: {report.sales}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ReportOne;
