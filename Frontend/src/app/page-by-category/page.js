'use client'

import React, { useState, useEffect } from "react";
import ProductCard from "../productcard/page";

const HomePage = ({searchParams}) => {
 
  useEffect(() => {
    // Check if the page has been refreshed before
    const hasBeenRefreshed = localStorage.getItem('hasBeenRefreshed');

    if (!hasBeenRefreshed) {
      // Mark the page as refreshed
      localStorage.setItem('hasBeenRefreshed', 'true');
      // Refresh the page only if it hasn't been refreshed before
      window.location.reload();
    }
  }, []);
  
  const scrollToFeaturedProducts = () => {
    // Find the position of the "Featured Products" section
    const featuredProductsSection = document.getElementById("featured-products");
    const yOffset = featuredProductsSection.getBoundingClientRect().top + window.scrollY;
    
    window.scrollTo({
      top: yOffset,
      behavior: 'smooth',
    });
  };



  const getProducts = async () => {
    
  
  

    try {
      //use categoryId to get products

      // const categoryIDFromParams = searchParams.get("categoryId");
      const categoryIDFromParams = searchParams.categoryId;
      
      
      const url = `http://localhost:8080/api/v1/categories/browse/${categoryIDFromParams}/products`;
  
      const response = await fetch(url);
      const products = await response.json();
      console.log(products);
      return products;
    } catch (error) {
      console.error("Error fetching products:", error);
      return [];
    }
  };

  // Use the useState hook to store the products
  const [products, setProducts] = useState([]);

  // Fetch and set the products when the component mounts
  useEffect(() => {
    const categoryID = searchParams.categoryId;
    
    if (categoryID) {
      getProducts(categoryID)
      .then((data) => setProducts(data));
       
    }
  }, []);

  return (
    <div className="bg-black">
              {/* Header Section */}
              <header
          className="bg-cover bg-center h-96"
          style={{
            backgroundImage: 'url("https://img.sdcexec.com/files/base/acbm/sdce/image/2020/02/GettyImages_961335672_ecommerce.5e597ee31feb6.png?auto=format%2Ccompress&q=70")',
          }}
        >
          
          <div className="bg-black opacity-50 h-full">
            {/* Header content */}
            <div className="container mx-auto h-full flex flex-col justify-center items-center text-white">
              <h1 className="text-4xl font-semibold">
                Welcome to C Store
              </h1>
              <p className="text-xl mt-4">Discover amazing products and deals.</p>
              {/* Add onClick function to scroll to featured products with animation */}
              <button
                onClick={scrollToFeaturedProducts}
                className="bg-black opacity-100 hover:bg-blue-900 text-white py-2 px-4 mt-6 rounded-full text-lg"
              >
                Explore Now
              </button>
            </div>
          </div>
        </header>
  
        {/* Featured Products Section */}
        <section id="featured-products" className="py-8">
          <div className="container mx-auto">
            <h2 className="text-2xl font-semibold mb-4">Featured Products</h2>
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
              {products.map((product) => (
                <ProductCard product={product} key={product.id} />
              ))}
            </div>
          </div>
        </section>
  
   
        <footer className="bg-gray-900 text-white py-4">
          <div className="container mx-auto text-center">
            <p>&copy; 2023 My C Store</p>
          </div>
        </footer>
    </div>
  );
};

export default HomePage;

  
