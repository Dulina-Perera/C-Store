const handler = async (req, res) => {
  const method = 'POST';


  const obj = req.body.obj;
  
  
    try {
      const response = await fetch('http://localhost:8080/api/v1/reg-user/carts/1/add', {
        method: 'POST',
        body: JSON.stringify(obj),
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const data = await response.json();
  
      console.log('Product added successfully:', data);
      return res.end(JSON.stringify(data));

      
    } catch (error) {
      console.error('Error while adding the product:', error);
    }

  };

export default handler;