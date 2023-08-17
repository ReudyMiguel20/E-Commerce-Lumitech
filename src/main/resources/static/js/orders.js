document.addEventListener("DOMContentLoaded", function () {
    const orderContainer = document.getElementById("order-container");

    const headers = {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("token"),
    }

    function fetchCartItem() {
        fetch("http://localhost:9090/api/orders", {
            method: "GET",
            headers: headers,
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);

                // Clear existing container before appending new orders
                orderContainer.innerHTML = '';

                for (const order of data) {
                    // Create a div for each order
                    const orderDiv = document.createElement("div");

                    // Add order information
                    const orderInfo = document.createElement("p");
                    orderInfo.innerHTML = `
                        <strong>Order Date:</strong> ${order.orderDate}<br>
                        <strong>Shipping Address:</strong> ${order.shippingAddress}<br>
                        <strong>Total:</strong> $${calculateOrderTotal(order).toFixed(2)}
                    `;
                    orderDiv.appendChild(orderInfo);

                    // Create a table for order items
                    const productTable = document.createElement("table");
                    productTable.className = "table";
                    const tableHeader = document.createElement("thead");
                    tableHeader.innerHTML = `
                        <tr>
                            <th scope="col">Product Name</th>
                            <th scope="col">Price</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Total Price</th>
                        </tr>
                    `;
                    productTable.appendChild(tableHeader);

                    const productBody = document.createElement("tbody");
                    for (const orderItem of order.orderItems) {
                        const productRow = document.createElement("tr");
                        productRow.innerHTML = `
                            <td>${orderItem.product.name}</td>
                            <td>${orderItem.product.price}</td>
                            <td>${orderItem.quantity}</td>
                            <td>${(orderItem.product.price * orderItem.quantity).toFixed(2)}</td>
                        `;
                        productBody.appendChild(productRow);
                    }
                    productTable.appendChild(productBody);

                    orderDiv.appendChild(productTable);
                    orderContainer.appendChild(orderDiv);
                }
            })
            .catch((error) => {
                console.error("Error fetching cart items:", error);
                // Throw error here if you want to
            });
    }

    function calculateOrderTotal(order) {
        return order.orderItems.reduce((total, orderItem) => {
            return total + orderItem.product.price * orderItem.quantity;
        }, 0);
    }

    fetchCartItem();
});
