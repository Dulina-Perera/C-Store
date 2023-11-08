const handler = async (req, res) => {
    const method = 'POST';


  
    
   
    
      
    const {varientList , productId} = req.body;
     
  
      try {
        const response = await fetch(`http://localhost:8080/api/v1/products/add/4/${parseInt(productId)}`, {
          method: 'POST',
          body: JSON.stringify(varientList),
          headers: {
            'Content-Type': 'application/json',
          },
        });

      
        return response.ok;

        
      } catch (error) {
        console.log("this happens")
        console.error('Error while adding the product:', error);
      }

    };

export default handler;

