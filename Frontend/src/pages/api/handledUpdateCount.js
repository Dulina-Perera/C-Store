const handler = async (req, res) => {
  const object = req.body.object;

  try {
    const response = await fetch(
      "http://localhost:8080/api/v1/reg-user/carts/1/update",
      {
        method: "PUT",
        body: JSON.stringify(object),
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    const data = await response.json();

    return res.end(JSON.stringify(data));
  } catch (error) {
    console.log("this happens");
    console.error("Error while adding the product:", error);
  }
};

export default handler;
