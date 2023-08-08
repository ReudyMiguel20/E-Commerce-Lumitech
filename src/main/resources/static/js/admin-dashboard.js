document.addEventListener("DOMContentLoaded", function () {
    productsTableBody = document.getElementById("products-table-body");

    const headers = {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("token"),
    }

    function fetchProducts() {
        fetch("http://localhost:9090/api/products", {
            method: "GET",
            headers: headers,
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);

                // Clear existing rows before appending new ones
                productsTableBody.innerHTML = '';

                for (const product of data) {
                    const productItem = document.createElement("tr");
                    productItem.innerHTML = `
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>${product.quantity}</td>
                    <td>
                    <button type="submit" class="btn btn-danger" data-product-id="${product.id}">Remove</button>
                    <button type="submit" class="btn btn-primary" data-product-id="${product.id}">Update</button>
                    </td>
                `;
                    productsTableBody.appendChild(productItem);
                }

                // Add event listeners to the "Remove" buttons after they are created
                const removeButtons = document.querySelectorAll(".btn-danger");
                const updateButtons = document.querySelectorAll(".btn-primary");
                removeButtons.forEach((button) => {
                    button.addEventListener("click", handleRemoveItem);
                });
                updateButtons.forEach((button) => {
                    button.addEventListener("click", handleUpdateItem);
                });
            })
            .catch((error) => {
                console.error("Error fetching products:", error);
            });


    }

    function handleRemoveItem(event) {
        const productId = event.target.getAttribute("data-product-id");
        const url = "http://localhost:9090/api/products/" + productId;

        fetch(url, {
            method: "DELETE",
            headers: headers,
        })
            .then((response) => {
                console.log(response);
                fetchProducts();
            })
            .catch((error) => {
                console.error("Error removing product:", error);
            });
    }

    function handleUpdateItem(event) {
        const productId = event.target.getAttribute("data-product-id");
        // Redirect the user to the update-product.html page with the product ID as a URL parameter
        window.location.href = `update-product.html`;
    }


    fetchProducts();

});