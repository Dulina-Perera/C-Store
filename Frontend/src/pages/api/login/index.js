// pages/api/login.js

export default (req, res) => {
    if (req.method === 'POST') {
      // Get the email and password from the request body
      const { email, password } = req.body;
  
      // Check if the email and password match a user in your system
      if (email === 'himindu@gmail.com' && password === 'pw123') {
        // If there's a match, return an example user object
        const user = {
          id: 1,
          name: 'Himindu',
          email: 'himindu@email.com',
          role: 'admin',
          address: '85 kaludewala panadura',
          phone_number: '077-6969481'
        };
  
        
        res.status(200).json(user);
      } else {
        // If the email and password do not match, respond with an error
        res.status(401).json({ error: 'Invalid email or password' });
      }
    } else {
      // Return a 405 Method Not Allowed if the request is not a POST request
      res.status(405).end();
    }
  };
  