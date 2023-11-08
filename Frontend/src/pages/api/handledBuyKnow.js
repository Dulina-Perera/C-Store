const handler = async (req, res) => {
  if (req.method === 'POST') {
    const { requestObject, userId } = req.body;

    try {
      const response = await fetch(`http://localhost:8080/api/v1/cust/orders/buy-now/${parseInt(userId)}`, {
        method: 'POST',
        body: JSON.stringify(requestObject),
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.ok) {
        const data = await response.json();
        console.log('Product added successfully:', data);
        return res.status(200).json(data);
      } else {
        console.error('Error while adding the product:', response.statusText);
        return res.status(500).send('Error while adding the product');
      }
    } catch (error) {
      console.error('Error while adding the product:', error);
      return res.status(500).send('Error while adding the product');
    }
  } else {
    res.status(405).send('Method Not Allowed');
  }
};

export default handler;
