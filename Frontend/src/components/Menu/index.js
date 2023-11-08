import React, { useState, useRef } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

const Menu = ({ isOpen, toggleMenu }) => {
  const router = useRouter();

  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [isSubCategoryOpen, setMenuOpen] = useState(false);
  const [hoveredCategory, setHoveredCategory] = useState(null);

  const menuRef = useRef(null);

  const toggleSubCategories = () => {
    setMenuOpen(!isSubCategoryOpen);
  };

  const handleCategoryClick = (category) => {
    setSelectedCategory(category);
    toggleMenu(); // Close the menu when a category is clicked
  };

 

  if (categories.length === 0) {
    // Fetch categories only if they are not loaded yet
    fetch('http://localhost:8080/api/v1/categories/browse/root')
      .then((response) => response.json())
      .then((data) => setCategories(data))
      .catch((error) => console.error('Error fetching categories:', error));
  }

  // Event listener to handle clicks outside the menu
 

  const handleLogout = () => {
    // Add your logout logic here, such as clearing session or tokens.
    // For example, you can use router.push('/login') to navigate to the login page.
    router.push('/login');
  };

  return (
    <div
      ref={menuRef}
      className={`fixed left-0 top-24 w-1/4 h-screen p-4 z-50 rounded-lg transition-transform transform ${
        isOpen ? 'translate-x-0' : '-translate-x-full'
      } bg-blue-900`}
    >
      <div className="text-white space-y-4">
        {categories.map((category) => (
          <div
            key={category.categoryId}
            onClick={() => handleCategoryClick(category)}
            onMouseEnter={() => setHoveredCategory(category)}
            onMouseLeave={() => setHoveredCategory(null)}
            className="cursor-pointer relative"
          >
            <div className="transition-transform transform hover:scale-3">
              <Link
                href={{
                  pathname: '/page-by-category',
                  query: { categoryId: category.categoryId }
                }}
              >
                <div className="bg-gray-700 hover-bg-gray-800 p-4 rounded-lg relative">
                  {category.categoryName}
                  {/* {hoveredCategory === category && (
                    <div className="bg-white bg-opacity-70 text-black p-4 rounded-lg relative top-0 left-4 w-60 mt-1 z-30">
                      <p>{category.categoryDescription}</p>
                    </div>
                  )} */}
                </div>
              </Link>
            </div>
          </div>
        ))}
      </div>
      <button
          className="text-white bg-red-600 hover:bg-red-800 p-4 rounded-lg w-full"
          onClick={handleLogout}
        >
          Logout
        </button>

    </div>
  );
};

export default Menu;
