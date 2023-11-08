'use client'
import React, { useEffect, useState } from "react";
import Link from "next/link";



function useUser() {
  // Retrieve and parse the user object from the local storage
  const userCookie = localStorage.getItem('user');
  const user = userCookie ? JSON.parse(userCookie) : null;

  return user;
}

const ProductView = ({ searchParams }) => {

  const [product, setProduct] = useState(null);
  const productId = searchParams.id;
  const [selectedProperties, setSelectedProperties] = useState({});
  const [isAllPropertiesSelected, setIsAllPropertiesSelected] = useState(false);
  const [isAddToCartClicked, setIsAddToCartClicked] = useState(false);
  const [totalPrice, setTotalPrice] = useState(0); // Track the total price


  const user = useUser();
  useEffect(() => {
    // Fetch the product data from your API endpoint
    fetch(`http://localhost:8080/api/v1/products/select/${productId}`)
      .then((response) => response.json())
      .then((data) => setProduct(data))
      .catch((error) => {
        console.error(error);
        setProduct(null);
      });
  }, [productId]);

  const organizeProperties = (productData) => {
    const propertyDictionary = {};

    if (productData && productData.properties) {
      productData.properties.forEach((property) => {
        const propertyName = property.propertyName;
        const propertyValue = property.value;
        const propertyId = property.propertyId;
        const priceIncrement = property.priceIncrement; 
        if (!propertyDictionary[propertyName]) {
          propertyDictionary[propertyName] = [];
        }

        propertyDictionary[propertyName].push({
          propertyValue,
          propertyId,
          priceIncrement,
        });
      });

      return propertyDictionary;
    }
  };

  const propertyDictionary = organizeProperties(product);

  const handlePropertySelection = (propertyName, propertyId, priceIncrement) => {
    setSelectedProperties((prevSelectedProperties) => ({
      ...prevSelectedProperties,
      [propertyName]: { propertyId, priceIncrement }, // Store the selected property with price increment
    }));
  };

  useEffect(() => {
    // Check if all properties are selected
    const propertiesCount = propertyDictionary
      ? Object.keys(propertyDictionary).length
      : 0;
    const selectedPropertiesCount = Object.keys(selectedProperties).length;
    setIsAllPropertiesSelected(propertiesCount === selectedPropertiesCount);

    // Calculate the total price when properties are selected
    let newTotalPrice = product ? product.basePrice : 0;
    Object.values(selectedProperties).forEach((property) => {
      newTotalPrice += property.priceIncrement;
    });
    setTotalPrice(newTotalPrice);
  }, [selectedProperties, propertyDictionary, product]);



  const buyNow = async () => {
    // Check if all properties are selected
    if (isAllPropertiesSelected) {
      try {
        const selectedPropertyIds = Object.values(selectedProperties).map(
          (property) => property.propertyId
        );
  
        const requestObject = {
          propertyIds: selectedPropertyIds,
          quantity: 1,
           // Include the user ID in the request
        };
        const userId = user.userId;
        console.log("requestObject :" ,requestObject);

  
        const response = await fetch('/api/handledBuyKnow', {
          method: 'POST',
          body: JSON.stringify({ requestObject, userId }), // Send the requestObject
          headers: {
            'Content-Type': 'application/json',
          },
        });

        console.log("response : " ,response);
  
        if (response.ok) {
          const data = await response.json();
          console.log(data);
          window.location.href = `/checkout?productID=${productId}&totalPrice=${totalPrice}&id=${data} `;
          
          // Redirect to the checkout page with appropriate data if needed
          // For example, you can use window.location or React Router.
        } else {
          console.error('Registration Error:', response.statusText);
        }
      } catch (error) {
        console.error('Registration Error:', error);
      }
    } else {
      setIsAddToCartClicked(true);
    }
  };


  const sendSelectedPropertiesToBackend = async() => {
    const selectedPropertyIds = Object.values(selectedProperties).map(
      (property) => property.propertyId
    );
    const obj = {
      propertyIds: selectedPropertyIds,
      quantity: 1,
    };
    console.log("obj :" ,obj);

    if (isAllPropertiesSelected) {
      try {
        const response = await fetch('/api/handledAddToCart', {
          method: 'POST',
        body: JSON.stringify( {obj} ), // Wrap formData in an object
          headers: {
            'Content-Type': 'application/json',
          },
        });
        console.log("response : " ,response);
        
    
        if (response.ok) {
          const data = await response.json();
          console.log(data);
          
        } else {
          console.error('Registration Error:', response.statusText);
        }
      } catch (error) {
        console.error('Registration Error:', error);
      }
    } else {
      setIsAddToCartClicked(true);
    }
  };

  // const goToCheckout = () => {
  //   // Navigate to the checkout page with selected properties
  //   router.push({
  //     pathname: 'checkout',
  //     query: { 
  //       productID : productId,
  //       totalPrice : totalPrice,
        
  //      },
  //   });
  // }
  console.log(selectedProperties);

  

  



  //create list of selected properties
  const selectedPropertiesList = Object.values(selectedProperties).map(
    (property) => property.propertyId
  );

  return (
    <div className="bg-gray-800 items-center py-8 flex justify-center">
      <div className="mx-auto items-center px-8">
        <div className="flex flex-row">
          {product ? (
            <div className="flex flex-wrap">
              <div className="md:flex-1 px-4">
                <div className="h-[460px] rounded-lg bg-gray-700 mb-4">
                  <img
                    src={product.imageUrl}
                    alt={product.productName}
                    className="w-full h-auto"
                  />
                </div>
                <div className="flex -mx-2 mb-4">
                  <div className="w-1/2 px-2">
                    <button
                      className={`w-full bg-gray-900 dark:bg-gray-600 text-white py-2 px-4 rounded-full font-bold ${
                        !isAllPropertiesSelected && isAddToCartClicked
                          ? "text-red-500"
                          : "hover:text-black"
                      } ${
                        !isAllPropertiesSelected && isAddToCartClicked
                          ? "hover:bg-transparent"
                          : "hover:bg-yellow-300"
                      }`}
                      onClick={sendSelectedPropertiesToBackend}
                    >
                      {isAllPropertiesSelected ? "Add to Cart" : "Select All Properties"}
                    </button>
                  </div>
                  <div className="w-1/2 px-2">
                    
                    
                      <button
                        className={`w-full bg-gray-700 text-white py-2 px-4 rounded-full font-bold ${
                          !isAllPropertiesSelected
                            ? "text-red-500"
                            : "hover:bg-gray-600"
                        }`}
                        onClick={buyNow}
                        
                        disabled={!isAllPropertiesSelected}
                      >
                        Buy Now
                      </button>
             
                   
                  </div>
                </div>
              </div>
              <div className="md:flex-1 px-4">
                <h2 className="text-2xl font-bold text-white mb-2">{product.productName}</h2>
                <p className="text-gray-300 text-sm mb-4">{product.description}</p>
                <div className="flex mb-4">
                  <div className="mr-4">
                    <p className="text-gray-600">Brand: {product.brand}</p>
                  </div>
                  <div className="mb-4">
                    <span className="font-bold text-gray-300">Price:</span>
                    <span className="text-gray-300">${totalPrice}</span>
                  </div>
                </div>
                <div>
                  <span className="font-bold text-gray-300">Availability:</span>
                  <span className="text-gray-300">In Stock Count: {product.stockCount}</span>
                </div>
                <div className="mb-4">
                  <h2 className="text-2xl text-gray-200 font-bold mb-4">Properties:</h2>
                  {Object.keys(propertyDictionary).map((propertyName) => (
                    <div key={propertyName} className="mb-4">
                      <span className="font-bold text-yellow-400 dark:text-gray-300">
                        {propertyName}:
                      </span>
                      <div className="flex items-center mt-2">
                        {propertyDictionary[propertyName].map((property) => (
                          <div
                            key={property.propertyId}
                            onClick={() =>
                              handlePropertySelection(
                                propertyName,
                                property.propertyId,
                                property.priceIncrement
                              )
                            }
                          >
                            <button
                              className={`${
                                selectedProperties[propertyName]?.propertyId === property.propertyId
                                  ? "text-black bg-yellow-400"
                                  : "text-gray-700 bg-gray-200"
                              } cursor-pointer bg-gray-700 text-white py-2 px-4 rounded-full font-bold mr-2`}
                            >
                              {property.propertyValue}
                            </button>
                          </div>
                        ))}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          ) : (
            <p className="text-lg">Loading...</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProductView;
