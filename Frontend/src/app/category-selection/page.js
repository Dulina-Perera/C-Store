'use client'
import React, { useState, useEffect } from "react";

const CategorySelection = ({ onCategorySelected, onSubcategorySelected }) => {
  const [categories, setCategories] = useState([]);
  const [subcategories, setSubcategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedSubcategory, setSelectedSubcategory] = useState(null);

  // Fetch categories from your API
  useEffect(() => {
    fetch("http://localhost:8080/api/v1/categories/browse/root")
      .then((response) => response.json())
      .then((data) => setCategories(data));
  }, []);

  // Fetch subcategories when a category is selected
  useEffect(() => {
    if (selectedCategory) {
      fetch(`http://localhost:8080/api/v1/categories/browse/${selectedCategory}/child`)
        .then((response) => response.json())
        .then((data) => setSubcategories(data));
    } else {
      setSubcategories([]);
    }
  }, [selectedCategory]);

  const handleCategoryChange = (e) => {
    setSelectedCategory(e.target.value);
    setSelectedSubcategory(null);
    onCategorySelected(e.target.value);
  };

  const handleSubcategoryChange = (e) => {
    setSelectedSubcategory(e.target.value);
    onSubcategorySelected(e.target.value);
  };

  return (
    <div className="mt-4">
      <select
        className="w-full p-2 border rounded"
        value={selectedCategory}
        onChange={handleCategoryChange}
      >
        <option value="">Select Category</option>
        {categories.map((category) => (
          <option key={category.id} value={category.id}>
            {category.name}
          </option>
        ))}
      </select>
      {subcategories.length > 0 && (
        <select
          className="w-full mt-2 p-2 border rounded"
          value={selectedSubcategory}
          onChange={handleSubcategoryChange}
        >
          <option value="">Select Subcategory</option>
          {subcategories.map((subcategory) => (
            <option key={subcategory.id} value={subcategory.id}>
              {subcategory.name}
            </option>
          ))}
        </select>
      )}
    </div>
  );
};

export default CategorySelection;
