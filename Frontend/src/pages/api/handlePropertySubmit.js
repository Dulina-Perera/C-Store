const handler = async (req, res) => {
    const method = 'POST';

    const propertyData = req.body.propertyList;
   
      
    
     
  
      try {
        const response = await fetch('http://localhost:8080/api/v1/products/edit/add/3', {
          method: 'POST',
          body: JSON.stringify(propertyData),
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





