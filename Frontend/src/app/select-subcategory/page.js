"use client";
import Link from "next/link";
import React, { useState, useEffect } from "react";

const SubCategory = ({ searchParams }) => {
    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState(null);
    const [selectedSubcategories, setSelectedSubcategories] = useState([parseInt(searchParams.categoryID, 10)]);
    const [productID, setProductID] = useState(searchParams.id); // Use "productID" here, not "productId"
    console.log(productID); // Log "productID" instead of "productID"
    
    // Rest of your component code
  
  

    const handledNextPage = async () => {
        console.log("function called")
        console.log(selectedSubcategories);
        console.log(productID); 
        
        

        try {
          const response = await fetch('/api/handleCategorySubmit', {
            method: 'POST', 
            body: JSON.stringify({
                "categoryList" : selectedSubcategories ,
                "productID" :   productID,
        }), 
            headers: {
              'Content-Type': 'application/json',
            },
          });
      
          console.log("response" );
          console.log(response);
      
          if (response.ok) {
            const data = await response.json();
            console.log('Product added successfully:', data);
          } else {
            console.error('Failed to add the product:', response.statusText);
          }
        } catch (error) {
          console.error('Error while adding the product:', error);
        }

        
    };
   

    const categoryId = searchParams.categoryID;
    
    console.log(selectedSubcategories);
    
    

    useEffect(() => {
        const fetchData = async () => {
            const res = await fetch(`http://localhost:8080/api/v1/categories/browse/${categoryId}/child`);
            const data = await res.json();
            setCategories(data);
        };
        fetchData();
    }, []);

    const handleCategorySelect = (category) => {
        setSelectedCategory(category);
    };

    const handleSubcategoryToggle = (categoryId) => {
        // Check if the category is already selected, and toggle its state
        if (selectedSubcategories.includes(categoryId)) {
            setSelectedSubcategories((prevSelected) =>
                prevSelected.filter((id) => id !== categoryId)
            );
        } else {
            setSelectedSubcategories((prevSelected) => [...prevSelected, categoryId]);
        }
    };

  

    return (
        <div className="p-4">
            <h1 className="text-3xl font-bold mb-4 text-center text-white">Sub Categories</h1>
            <div className="flex flex-wrap justify-center">
                {categories.map((category) => (
                    <div key={category.categoryId} className="mb-4 bg-blue-900 rounded-lg p-4 w-80 mx-4">
                        <h2 className="text-xl font-semibold text-white">
                            {category.categoryName}
                        </h2>
                        <p className="text-gray-300">{category.categoryDescription}</p>
                        <input
                            type="checkbox"
                            checked={selectedSubcategories.includes(category.categoryId)}
                            onChange={() => handleSubcategoryToggle(category.categoryId)}
                        />
                        <button onClick={() => handleCategorySelect(category)}>
                            Select
                        </button>
                    </div>
                ))}
            </div>
            
            <div className="text-center mt-4">
            
                <button onClick={handledNextPage} className="bg-blue-100 text-white py-2 px-4 rounded-lg mt-4">
                    Submit
                </button>

            </div>

            <Link href={{
                pathname: "/property-select",
                query: {
                    id: productID,
                }
            }}
            >
               
                <button className="bg-blue-100 text-white py-2 px-4 rounded-lg mt-4">
                    Go to Property Selection
                </button>
                    
                
            </Link>
         
        </div>
    );
};

export default SubCategory;

