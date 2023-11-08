'use client'
import React, { useState } from 'react';

const ResponseComponent = ({ responseData , productId}) => {
  const [variantCount, setVariantCount] = useState(1); // Set to 2 for two variants
  const [selectedValues, setSelectedValues] = useState({});
  const [variantWeights, setVariantWeights] = useState({});
  const [generatedList, setGeneratedList] = useState([]);
  

  const addNewVariant = () => {
    setVariantCount(variantCount + 1);
  };

  const handleWeightChange = (variantIndex, weight) => {
    setVariantWeights((prevWeights) => ({
      ...prevWeights,
      [variantIndex]: weight,
    }));
  };

  const arraysEqual = (a, b) => {
    if (a.length !== b.length) return false;
    for (let i = 0; i < a.length; i++) {
      if (a[i] !== b[i]) return false;
    }
    return true;
  };

  const isDuplicate = (item, otherItem) => {
    return (
      item.weight === otherItem.weight ||
      arraysEqual(item.propertyIds.sort(), otherItem.propertyIds.sort())
    );
  };

  const generateList = async () => {
    const list = [];

    const properties = Object.keys(selectedValues);
    const propertyIds = properties.map((property) =>
      responseData[property].find((item) => selectedValues[property] === item.value)
        .propertyId
    );

    for (let variantIndex = 0; variantIndex < variantCount; variantIndex++) {
      const weight = variantWeights[variantIndex] || 0;

      for (let i = 0; i < propertyIds.length; i++) {
        for (let j = i + 1; j < propertyIds.length; j++) {
          list.push({
            weight: weight,
            propertyIds: [propertyIds[i], propertyIds[j]],
          });
        }
      }
    }
    console.log("list" , list);

    // Remove duplicates by weight and propertyIds
    const uniqueList = list.filter((item, index, self) => {
      return !self.slice(index + 1).some((otherItem) => isDuplicate(item, otherItem));
    });
    
    console.log("pid" , productId);
   

    const listToDisplay = uniqueList;
    console.log("listToDisplay" , listToDisplay)
    try {
        const response = await fetch("/api/handledVarientAdd", {
          method: "POST",
          body: JSON.stringify({
            varientList: list,
            productId : productId,
          }),
          headers: {
            "Content-Type": "application/json",
          },
        });
  
        if (response.ok) {
            const data = await response.json();
            console.log("this is the response :", data);
            setGeneratedList(data);
          
        } else {
          console.error("Failed to add the product:", response.statusText);
        }
      } catch (error) {
        console.error("Error while adding the product:", error);
      }



  };

  return (
    <div className="bg-gray-900  p-4 mt-4 rounded-lg">


  {Array.from({ length: variantCount }).map((_, index) => (
    <div key={index}>
      <h2 className="text-lg font-semibold mb-4">Variant {index + 1}</h2>
      <div className="mb-4">
        <label htmlFor={`weight${index}`} className="text-yellow-100">
          Weight for Variant {index + 1}:
        </label>
        <input
          type="text"
          id={`weight${index}`}
          value={variantWeights[index] || ''}
          onChange={(e) => handleWeightChange(index, e.target.value)}
          className="w-full p-2 border rounded-lg text-gray-900"
        />
      </div>
      <form className="flex w-full flex-col">
        {Object.keys(responseData).map((property) => (
          <div key={property} className="mb-4">
            <h3 className="text-md font-semibold mb-2 text-yellow-100">{property}</h3>
            {responseData[property].map((item) => (
              <div key={item.propertyId} className="mb-2">
                <input
                  type="radio"
                  id={item.propertyId}
                  name={property}
                  value={item.value}
                  onChange={(e) => {
                    setSelectedValues((prevSelectedValues) => ({
                      ...prevSelectedValues,
                      [property]: e.target.value,
                    }));
                  }}
                  className="mr-2"
                />
                <label htmlFor={item.propertyId} className="text-yellow-100">
                  {item.value}
                </label>
              </div>
            ))}
          </div>
        ))}
      </form>
    </div>
  ))}

<button onClick={addNewVariant} className="bg-yellow-400 text-gray-900 p-2 rounded-lg mb-2 mr-2">
  Add New Variant
</button>
<button onClick={generateList} className="bg-yellow-400 text-gray-900 p-2 rounded-lg mb-2">
  Generate List
</button>


  <div>
    <h2 className="text-lg font-semibold mb-4 text-yellow-100">Generated List</h2>
    <pre>{JSON.stringify(generatedList, null, 2)}</pre>
  </div>
</div>

  );
};

export default ResponseComponent;
