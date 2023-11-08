// pages/properties.js
'use client'
import React, { useState } from "react";
import { useDropzone } from "react-dropzone";
import ResponseComponent from "../../components/VarientAdd";

const properties = ["color", "size", "warranty", "storage"];

const PropertyList = ({ searchParams }) => {
  const [propertyData, setPropertyData] = useState({});
  const [productID, setProductID] = useState(searchParams.id);
  const [responseData, setResponseData] = useState(null);

  console.log("product id:", productID);

  const handleAddValue = (property) => {
    setPropertyData((prevData) => ({
      ...prevData,
      [property]: {
        values: [...(prevData[property]?.values || []), ""],
        prices: [...(prevData[property]?.prices || []), 0],
        images: [...(prevData[property]?.images || []), null],
      },
    }));
  };

  const handleRemoveValue = (property, valueIndex) => {
    setPropertyData((prevData) => {
      const newData = { ...prevData };
      newData[property].values.splice(valueIndex, 1);
      newData[property].prices.splice(valueIndex, 1);
      newData[property].images.splice(valueIndex, 1);
      return newData;
    });
  };

  const handleUpdateValue = (property, index, value, price, image) => {
    setPropertyData((prevData) => {
      const newData = { ...prevData };
      newData[property].values[index] = value;
      newData[property].prices[index] = price;
      newData[property].images[index] = image;
      return newData;
    });
  };

  const onDrop = (acceptedFiles, property, valueIndex) => {
    const file = acceptedFiles[0];
    const imageUrl = URL.createObjectURL(file);
    handleUpdateValue(
      property,
      valueIndex,
      propertyData[property].values[valueIndex],
      propertyData[property].prices[valueIndex],
      imageUrl
    );
  };

  const prepareDataForBackend = async () => {
    const dataForBackend = [];
    console.log("product id:", productID);

    properties.forEach((property) => {
      if (propertyData[property]) {
        const values = propertyData[property].values;
        const prices = propertyData[property].prices;
        const images = propertyData[property].images;

        for (let i = 0; i < values.length; i++) {
          dataForBackend.push({
            propertyName: property,
            value: values[i],
            imageUrl: images[i],
            priceIncrement: prices[i],
          });
        }
      }
    });

    try {
      const response = await fetch("/api/handlePropertySubmit", {
        method: "POST",
        body: JSON.stringify({
          propertyList: dataForBackend,
        }),
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        const data = await response.json();
        console.log("Product added successfully:", data);

        // Set the response data in state
        setResponseData(data);
      } else {
        console.error("Failed to add the product:", response.statusText);
      }
    } catch (error) {
      console.error("Error while adding the product:", error);
    }
  };

  const sendDataToBackend = () => {
    prepareDataForBackend();
  };

  const { getRootProps, getInputProps } = useDropzone({
    onDrop: (acceptedFiles) => onDrop(acceptedFiles, "color", 0),
    accept: "image/*",
    multiple: false,
  });

  // Placeholder for the function to navigate to the next page or component
  const goToNextPage = () => {
    // Implement the logic to go to the next page or component
    // For example, you can use React Router for navigation.
  };

  return (
    <div className="p-4 bg-gray-900">
      <h1 className="text-2xl text-yellow-100 font-bold mb-4">Product Properties</h1>
      <ul>
        {properties.map((property, index) => (
          <li key={index} className="mb-4">
            <h2 className="text-lg font-semibold text-yellow-300 mb-2">
              {property}
            </h2>
            <button
              onClick={() => handleAddValue(property)}
              className="bg-yellow-700 text-white px-3 py-1 rounded-md hover:bg-yellow-500"
            >
              Add Value
            </button>
            {propertyData[property] &&
              propertyData[property].values.map((value, valueIndex) => (
                <div key={valueIndex} className="mt-4 border p-4 rounded-lg">
                  <div className="flex items-center mb-2">
                    <input
                      type="text"
                      placeholder="Value"
                      value={value}
                      onChange={(e) =>
                        handleUpdateValue(
                          property,
                          valueIndex,
                          e.target.value,
                          propertyData[property].prices[valueIndex],
                          propertyData[property].images[valueIndex]
                        )
                      }
                      className="border border-gray-300 px-2 py-1 rounded-md mr-2"
                    />
                    <input
                      type="number"
                      placeholder="Price"
                      value={propertyData[property].prices[valueIndex]}
                      onChange={(e) =>
                        handleUpdateValue(
                          property,
                          valueIndex,
                          propertyData[property].values[valueIndex],
                          parseFloat(e.target.value) || 0,
                          propertyData[property].images[valueIndex]
                        )
                      }
                      className="border border-gray-300 px-2 py-1 rounded-md mr-2"
                    />
                    <button
                      onClick={() => handleRemoveValue(property, valueIndex)}
                      className="bg-red-500 text-white px-2 py-1 rounded-md hover-bg-red-600"
                    >
                      Remove
                    </button>
                  </div>
                  <div
                    {...getRootProps()}
                    className="bg-gray-100 p-4 rounded-md"
                  >
                    <input {...getInputProps()} />
                    <p className="text-center text-gray-400">
                      Drag 'n' drop an image here, or click to select one
                    </p>
                  </div>
                  {propertyData[property].images[valueIndex] && (
                    <img
                      src={propertyData[property].images[valueIndex]}
                      alt={`Image for ${property} - ${value}`}
                      className="max-w-xs my-2"
                    />
                  )}
                </div>
              ))}
          </li>
        ))}
      </ul>
      <button
        onClick={sendDataToBackend}
        className="relative bottom-4 right-4 bg-green-500 text-white px-4 py-2 rounded-md"
      >
        Save Data to Backend
      </button>
      <button
        onClick={goToNextPage}
        className="relative bottom-4 right-15 bg-blue-500 text-white px-4 py-2 rounded-md"
      >
        Next Page
      </button>
      {responseData && <ResponseComponent responseData={responseData} productId={productID}/>}
    </div>
  );
};

export default PropertyList;

