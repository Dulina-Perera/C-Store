import React from 'react';

const UserProfilePage = () => {
  const user = {
    f_name: "himindu",
    l_name: "kularathne",
    address: {
      Street: "hill street",
      city: "Colombo",
      zip: "234"
    },
    e_mail: "himindu.21@cse.mrt.ac.lk",
    phone_number: "0776969481"
  };

  return (
    <div className="bg-gray-100 p-4 shadow-md">
      <h1 className="text-2xl font-semibold">User Profile</h1>
      <p className="text-gray-700"><strong>First Name:</strong> {user.f_name}</p>
      <p className="text-gray-700"><strong>Last Name:</strong> {user.l_name}</p>
      <p className="text-gray-700"><strong>Address:</strong> {user.address.Street}, {user.address.city}, {user.address.zip}</p>
      <p className="text-gray-700"><strong>Email:</strong> {user.e_mail}</p>
      <p className="text-gray-700"><strong>Phone Number:</strong> {user.phone_number}</p>
    </div>
  );
};

export default UserProfilePage;
