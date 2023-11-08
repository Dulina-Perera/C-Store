'use client'
import ProductForm from "../Product-add-1/add-product-component";
import { useState } from "react";

const AddProductPage = () => {
  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedSubcategory, setSelectedSubcategory] = useState("");

  const handleCategorySelected = (category) => {
    setSelectedCategory(category);
  };

  const handleSubcategorySelected = (subcategory) => {
    setSelectedSubcategory(subcategory);
  };

    
 
    
  return (
    <>
    <h1 className="text-4xl font-bold text-center text-white mt-40">Add Product</h1>

    <div className="container relative top-10 bg-black rounded-lg mx-auto p-4">
    <ProductForm  />

    </div>
    </>
  );
};

export default AddProductPage;
