"use client";
import Link from "next/link";
import React, { useState, useEffect } from "react";

const Categories = () => {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/v1/categories/browse/root");
        if (!res.ok) {
          throw new Error("Failed to fetch data");
        }
        const data = await res.json();
        setCategories(data);
        console.log(data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchData();
  }, []);
  
  return (
    <div className="p-4">
      <h1 className="text-3xl font-bold mb-4 text-center text-white">Categories</h1>
      <div className="flex flex-wrap justify-center">
        {categories.map((category) => (
          <Link
            href={{
              pathname: "/select-subcategory",
              query: {
                categoryID: category.categoryId,
              },
            }}
          >
            <div
              key={category.categoryId}
              className="mb-4 bg-blue-900 rounded-lg p-4 w-80 mx-4"
            >
              <h2 className="text-xl font-semibold text-white">
                {category.categoryName}
              </h2>
              <p className="text-gray-300">{category.categoryDescription}</p>
            </div>
          </Link>
        ))}
      </div>
      </div>
      
     
  );
};

export default Categories;
