"use client";

import React, { useState } from "react";

function AddProduct2({ searchParams }) {
  const [divisions, setDivisions] = useState([]);
  const [variants, setVariants] = useState([]);
  console.log(searchParams);

  const addDivision = () => {
    const newVariant = {
      weight: "",
      properties: {},
    };

    searchParams.productProperties.forEach((property) => {
      newVariant.properties[property] = "";
    });

    setVariants([...variants, newVariant]);
  };

  const handleWeightChange = (index, value) => {
    const updatedVariants = [...variants];
    updatedVariants[index].weight = value;
    setVariants(updatedVariants);
  };

  const handlePropertyChange = (index, property, value) => {
    const updatedVariants = [...variants];
    updatedVariants[index].properties[property] = value;
    setVariants(updatedVariants);
  };

  const submitVariants = () => {
    // Send variants to the server
    // You should implement your own logic for posting data to the backend here
    console.log("Variants to be sent to the server:", variants);
  };

  return (
    <div>
     <div className="flex justify-center items-center ">
      <h1 className="text-2xl font-bold text-white mb-6">Add Variants</h1>
      </div>
      <div className="container mx-auto py-100">
        {variants.map((variant, index) => (
          
          <div className="flex justify-center items-center">
                <div className="border border-gray-300 rounded  p-4 my-10 sm:w-3/4 md:w-2/3 lg:w-1/2 xl:w-1/3 bg-blue-100">

        
          
            <h3 className="text-gray-700 text-lg font-semibold mb-4">Weight</h3>
            <input
              type="text"
              placeholder="Enter weight"
              className="border border-gray-400 rounded p-2 w-full mb-4"
              value={variant.weight}
              onChange={(e) => handleWeightChange(index, e.target.value)}
            />
            {searchParams.productProperties.map((property, propertyIndex) => (
              <div key={propertyIndex}>
                <h3 className="text-gray-700 text-lg font-semibold mb-4">
                  {property}
                </h3>
                {property === "Color" ? (
                  <ColorPicker
                    value={variant.properties[property]}
                    onChange={(value) =>
                      handlePropertyChange(index, property, value)
                    }
                  />
                ) : (
                  <input
                    type="text"
                    placeholder={`Enter ${property}`}
                    className="border border-gray-400 rounded p-2 w-full mb-4"
                    value={variant.properties[property]}
                    onChange={(e) =>
                      handlePropertyChange(index, property, e.target.value)
                    }
                  />
                )}
              </div>
            ))}
            </div>
          </div>
        ))}
        <div className="flex justify-center space-x-4">
          <button
            onClick={addDivision}
            className="bg-blue-900 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
          >
            Add Variant
          </button>
          <button
            onClick={submitVariants}
            className="bg-green-900 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full"
          >
            Submit Variants
          </button>
        </div>
      </div>
    </div>
  );
}

function ColorPicker({ value, onChange }) {
  return (
    <div>
      <input
        type="color"
        className="w-8 h-8 rounded-full focus:outline-none cursor-pointer"
        value={value}
        onChange={(e) => onChange(e.target.value)}
      />
    </div>
  );
}

export default AddProduct2;
