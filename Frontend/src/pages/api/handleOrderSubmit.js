const handler = async (req, res) => {
    const method = 'POST';
      
    const { categoryList, productID } = req.body;
     
  
      try {
        const response = await fetch(`http://localhost:8080/api/v1/orders/confirm/${parseInt(productID)}}`, {
          method: 'POST',
          body: JSON.stringify(categoryList),
          headers: {
            'Content-Type': 'application/json',
          },
        });

        const data = await response.json();
        console.log('Product added successfully:', data);
        return res.end(JSON.stringify(data));

        
      } catch (error) {
        console.log("this happens")
        console.error('Error while adding the product:', error);
      }

    };

export default handler;





