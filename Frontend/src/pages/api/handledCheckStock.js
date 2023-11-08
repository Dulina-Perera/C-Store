const handler = async (req, res) => {
    const method = 'POST';

    const variantList = req.body.variantList;
      
    try {
        const response = await fetch('http://localhost:8080/api/v1/carts/1/refresh', {
          method: 'POST',
          body: JSON.stringify(variantList),
          headers: {
            'Content-Type': 'application/json',
          },
        });
  
        if (response.ok) {
          const data = await response.json();
          return res.end(JSON.stringify(data));
        }
      } catch (error) {
        console.error('Error while refreshing the product:', error);
        res.status(500).end('Internal Server Error');
      }


};
export default handler;


