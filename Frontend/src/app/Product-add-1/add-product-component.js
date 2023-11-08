// components/ProductForm.js
'use client'
import { useState , useEffect} from 'react';
import Link from 'next/link';



const ProductForm = () => {
  const [product, setProduct] = useState({
    productName: '',
    basePrice: 0,
    brand: '',
    description: '',
    mainImageUrl: '',
    otherImageUrls: [],
  });
  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedSubcategory, setSelectedSubcategory] = useState("");
  const [productID, setProductID] = useState(null);


  const [showCategorySelection, setShowCategorySelection] = useState(false);
  

  const handleCategorySelected = (category) => {
    setSelectedCategory(category);
  };

  const handleSubcategorySelected = (subcategory) => {
    setSelectedSubcategory(subcategory);
  };


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProduct({
      ...product,
      [name]: value,
    });
  };

  const handleMainImageUpload = (e) => {
    const file = e.target.files[0];
    
  };

  const handleOtherImagesUpload = (e) => {
    const files = e.target.files;
   
  };



  const handleSubmit = async (e) => {
    e.preventDefault();
  
    // Prepare the product data to be sent to the server
    const formData = {
   
        productName: product.productName,
        basePrice: product.basePrice,
        brand: product.brand,
        description: product.description,
        mainImageUrl: '/hdna/aksdfna', // Ensure this is correct
        otherImageUrls: product.otherImageUrls,
      
    };
  
    console.log('Product data to be sent:', formData);
  
    try {
      const response = await fetch('/api/handledPostBasicData', {
        method: 'POST', 
        body: JSON.stringify({"formData" : formData}), 
        headers: {
          'Content-Type': 'application/json',
        },
      });
  
      const data = await response.json();
      console.log("this is the response :", data);
      setProductID(data);
      setShowCategorySelection(true)
      

      

  
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
  

  return (
    <>
    <form onSubmit={handleSubmit} className="max-w-md mx-auto mt-4 p-4 bg-white rounded-lg shadow-md">
      <div className="mb-4">
        <label htmlFor="productName" className="block text-gray-700">Product Name:</label>
        <input
          type="text"
          name="productName"
          value={product.productName}
          onChange={handleInputChange}
          className="w-full border border-gray-300 rounded p-2"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="basePrice" className="block text-gray-700">Base Price:</label>
        <input
          type="number"
          name="basePrice"
          value={product.basePrice}
          onChange={handleInputChange}
          className="w-full border border-gray-300 rounded p-2"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="brand" className="block text-gray-700">Brand:</label>
        <input
          type="text"
          name="brand"
          value={product.brand}
          onChange={handleInputChange}
          className="w-full border border-gray-300 rounded p-2"
        />
      </div>
      <div className="mb-4">
        <label htmlFor="description" className="block text-gray-700">Description:</label>
        <textarea
          name="description"
          value={product.description}
          onChange={handleInputChange}
          className="w-full border border-gray-300 rounded p-2"
        />
      </div>
      <div className="mb-4">
        <label className="block text-gray-700">Main Image:</label>
        <input type="file" accept="image/*" onChange={handleMainImageUpload} className="w-full" />
      </div>
      <div className="mb-4">
        <label className="block text-gray-700">Other Images:</label>
        <input type="file" accept="image/*" multiple onChange={handleOtherImagesUpload} className="w-full" />
      </div>
      <button type="submit" className="bg-blue-500 text-white font-semibold py-2 px-4 rounded hover:bg-blue-600">
        Add Product
      </button>
      

     
    </form>
    <div className="container relative top-10 bg-black rounded-lg mx-auto p-4">
    {showCategorySelection && (
      <CategorySelection  productId={productID}
          
        />
      )}
     </div>
     </>
  );
};

export default ProductForm;

const CategorySelection = ({productId}) => {
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
                id : parseInt(productId),
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
