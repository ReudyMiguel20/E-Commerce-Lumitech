document.addEventListener("DOMContentLoaded", function () {
    cartProduct = document.getElementById("cart");
    cartProductsTable = document.getElementById("cart-products");
    total = document.getElementById("total");
    clearCartButton = document.getElementById("clear-cart");

    const headers = {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("token"),
    }

    function fetchCart() {
        fetch("http://localhost:9090/api/cart", {
            method: "GET",
            headers: headers,
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);

                // Clear existing rows before appending new ones
                cartProductsTable.innerHTML = '';

                for (const product of data.productList) {
                    const productItem = document.createElement("tr");
                    productItem.innerHTML = `
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>${product.quantity}</td>
                    <td>-  /   +</td>
                    <td><button type="submit" class="btn btn-danger" data-product-name="${product.name}">Remove</button></td>
                `;
                    cartProductsTable.appendChild(productItem);
                }

                total.innerHTML = data.total;

                // Add event listeners to the "Remove" buttons after they are created
                const removeButtons = document.querySelectorAll(".btn-danger");
                removeButtons.forEach((button) => {
                    button.addEventListener("click", handleRemoveItem);
                });
            })
            .catch((error) => {
                console.error("Error fetching cart:", error);
            });
    }

    function handleRemoveItem(event) {
        const productName = event.target.getAttribute("data-product-name");

        const deleteData = {
            productname: productName
        };

        fetch("http://localhost:9090/api/cart", {
            method: "DELETE",
            headers: headers,
            body: JSON.stringify(deleteData),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log(data);
                // After successful deletion, fetch the updated cart
                fetchCart();
            })
            .catch((error) => {
                console.error("Error removing item:", error);
            });
    }

    function clearCart() {
        fetch("http://localhost:9090/api/cart/empty", {
            method: "DELETE",
            headers: headers,
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log(data);
                // After successful deletion, fetch the updated cart
                fetchCart();
            })
            .catch((error) => {
                console.error("Error removing item:", error);
            });
    }

    clearCartButton.addEventListener("click", clearCart);

    fetchCart();
});