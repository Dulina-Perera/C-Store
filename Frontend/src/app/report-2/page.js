'use client'
import React, { useEffect, useState } from 'react';

const ReportTwo = () => {
  const [reportData, setReportData] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/v1/all/reports/categories/orders/max')
      .then((response) => response.json())
      .then((data) => setReportData(data))
      .catch((error) => console.error(error));
  }, []);

  return (
    <div className="bg-gray-900 h-screen flex items-center justify-center">
      <div className="flex flex-wrap">
        {reportData.map((report) => (
          <div key={report.categoryId} className="m-4">
            <div className="bg-yellow-400 p-4 rounded-lg shadow-md">
              <h2 className="text-lg text-gray-900 font-semibold">{report.categoryName}</h2>
              <p className="text-gray-900 mt-2">{report.categoryDescription}</p>
              <p className="text-gray-900 mt-2">Order Count: {report.orderCount}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ReportTwo;
