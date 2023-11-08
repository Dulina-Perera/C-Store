import React from 'react';
import Link from 'next/link';

const ReportComponent = () => {
  return (
    <div className="bg-gray-900 h-screen flex items-center justify-center">
    
      <div className="flex">
      <Link href = "report-1">
        <div className="hover:cursor-pointer bg-yellow-400 p-4 m-4 rounded-lg">
          <p>Report 1</p>
        </div>
        </Link>
        <Link href = "report-2">
        <div className="hover:cursor-pointer bg-yellow-400 p-4 m-4 rounded-lg">
          <p>Report 2</p>
        </div>
        </Link>
        
       
      </div>
    </div>
  );
};

export default ReportComponent;
