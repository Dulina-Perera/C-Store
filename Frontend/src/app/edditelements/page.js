'use client'
import React, { useState } from 'react';

const item = {
  itemID: "1",
  name: "iphone",
  description: "this is an iPhone",
  brand: "brand",
  basephoto: "https://cdn.dxomark.com/wp-content/uploads/medias/post-126771/Apple-iPhone-14-Pro_FINAL_featured-image-packshot-review-1.jpg",
  baseprice: "2000",
  varient: [
    {
      varient_id: "12",
      varient_photo: "https://www.apple.com/newsroom/images/product/iphone/standard/Apple-iPhone-14-Pro-iPhone-14-Pro-Max-deep-purple-220907_inline.jpg.large.jpg",
      variet_description: "this is the red 64GB variant",
      varient_price: 45000
    },
    {
      varient_id: "122",
      varient_photo: "https://www.apple.com/newsroom/images/product/iphone/standard/Apple-iPhone-14-Pro-iPhone-14-Pro-Max-deep-purple-220907_inline.jpg.large.jpg",
      variet_description: "this is the black 128GB variant",
      varient_price: 50000
    }
  ]
};

const EditElements = () => {
  const [editing, setEditing] = useState(false);
  const [editedItem, setEditedItem] = useState(item);

  const handleToggleEdit = () => {
    setEditing(!editing);
  };

  const handleSave = () => {
    setEditing(false);
    // You can save the editedItem here and update the original item
  };

  const handleCancelEdit = () => {
    setEditedItem(item); // Reset the editedItem to the original item
    setEditing(false);
  };

  return (
    <div className="container mx-auto mt-8 px-4 sm:px-6 lg:px-8 bg-black">
      <div className="max-w-4xl mx-auto p-4 border rounded-lg text-center bg-white">
        <h1 className="text-2xl font-semibold mb-4">Product Edit page</h1>
        <div className="flex justify-end">
          {editing ? (
            <>
             <button
                onClick={() => handleSave()}
                className="bg-blue-500 text-white px-2 py-1 rounded mr-2"
              >
                Save
              </button>
              <button
                onClick={() => handleCancelEdit()}
                className="bg-red-500 text-white px-2 py-1 rounded"
              >
                Cancel Edit
              </button>
            </>
          ) : (
            <button
              onClick={() => handleToggleEdit()}
              className="bg-gray-300 text-gray-700 px-2 py-1 rounded"
            >
              Edit
            </button>
          )}
        </div>
        <div className="flex flex-col md:flex-row items-center space-x-4">
          <div className="md:w-1/2">
            {/* Main photo */}
            <img src={editing ? editedItem.basephoto : item.basephoto} alt={item.name} className="w-full h-auto object-cover rounded-lg" />
          </div>
          <div className="md:w-1/2 text-left">
            <h2 className="text-xl font-semibold">{editing ? (
              <input
                type="text"
                value={editedItem.name}
                onChange={(e) => setEditedItem({ ...editedItem, name: e.target.value })}
                placeholder={item.name}
              />
            ) : item.name}</h2>
            <p className="text-gray-600 mt-2">
              Description: {editing ? (
                <input
                  type="text"
                  value={editedItem.description}
                  onChange={(e) => setEditedItem({ ...editedItem, description: e.target.value })}
                  placeholder={item.description}
                />
              ) : item.description}
            </p>
            <p className="text-gray-600 mt-2">
              Brand: {editing ? (
                <input
                  type="text"
                  value={editedItem.brand}
                  onChange={(e) => setEditedItem({ ...editedItem, brand: e.target.value })}
                  placeholder={item.brand}
                />
              ) : item.brand}
            </p>
            <p className="text-gray-600 mt-2">
              Base Price: Rs.{editing ? (
                <input
                  type="text"
                  value={editedItem.baseprice}
                  onChange={(e) => setEditedItem({ ...editedItem, baseprice: e.target.value })}
                  placeholder={item.baseprice}
                />
              ) : item.baseprice}
            </p>
          </div>
        </div>
        <h3 className="text-lg font-semibold mt-4">Variants:</h3>
        <ul className="list-disc mx-auto text-left w-3/4">
          {item.varient.map((variant, index) => (
            <li key={variant.varient_id} className="mt-4 border p-4 rounded-lg">
              <div className="flex flex-col md:flex-row items-center space-x-4">
                <div className="md:w-1/2">
                  {/* Variant photo */}
                  <img src={editing ? editedItem.varient[index].varient_photo : variant.varient_photo} alt={item.name} className="w-full h-auto object-cover rounded-lg" />
                </div>
                <div className="md:w-1/2 text-left">
                  <h4 className="text-gray-600 mt-2">
                    description: {editing ? (
                    <input
                      type="text"
                      value={editedItem.varient[index].variet_description}
                      onChange={(e) => {
                        const updatedVariants = [...editedItem.varient];
                        updatedVariants[index].variet_description = e.target.value;
                        setEditedItem({ ...editedItem, varient: updatedVariants });
                      }}
                      placeholder={variant.variet_description}
                    />
                  ) : variant.variet_description}</h4>
                  <p className="text-gray-600 mt-2">
                    Price: Rs.{editing ? (
                      <input
                        type="text"
                        value={editedItem.varient[index].varient_price}
                        onChange={(e) => {
                          const updatedVariants = [...editedItem.varient];
                          updatedVariants[index].varient_price = e.target.value;
                          setEditedItem({ ...editedItem, varient: updatedVariants });
                        }}
                        placeholder={variant.varient_price}
                      />
                    ) : variant.varient_price}
                  </p>
                </div>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default EditElements;
