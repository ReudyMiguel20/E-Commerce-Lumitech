document.addEventListener("DOMContentLoaded", function () {
    const updateProductForm = document.getElementById("product-form");
    const productId = new URLSearchParams(window.location.search).get("product_id");

    const headers = {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("token"),
    };

    // Function to fetch the product data based on the product ID
    function fetchProductData(productId) {
        const apiUrl = `http://localhost:9090/api/products/${productId}`; // Replace with your API endpoint
        fetch(apiUrl, {
            method: "GET",
            headers: headers,
        })
            .then((response) => response.json())
            .then((product) => {
                populateFormFields(product); // Populate the form with the fetched data
            })
            .catch((error) => {
                console.error("Error fetching product data:", error);
            });
    }

    // Function to populate the form fields with data from the fetched product
    function populateFormFields(product) {
        document.getElementById("name").value = product.name;
        document.getElementById("price").value = product.price;
        document.getElementById("description").value = product.description;
        document.getElementById("category").value = product.category;
        document.getElementById("image").value = product.image;
        document.getElementById("brand").value = product.brand;
        document.getElementById("stock").value = product.stock;
        // You can add more input fields for other product attributes as needed
    }

    // Fetch the product data and populate the form when the page is loaded
    fetchProductData(productId);

    // Add event listener to the form for submitting the updated data
    updateProductForm.addEventListener("submit", function (event) {
        event.preventDefault();
        // Get the updated data from the form fields
        const updatedData = {
            name: document.getElementById("name").value,
            price: parseFloat(document.getElementById("price").value),
            description: document.getElementById("description").value,
            category: document.getElementById("category").value,
            image: document.getElementById("image").value,
            brand: document.getElementById("brand").value,
            stock: parseInt(document.getElementById("stock").value),
            // You can add more properties for other product attributes as needed
        };

        // Add code here to submit the updated data to the API using a PUT or PATCH request
        fetch(`http://localhost:9090/api/products/${productId}`, {
            method: "PUT", // Replace with your desired HTTP method (PUT or PATCH)
            headers: headers,
            body: JSON.stringify(updatedData),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log("Product updated successfully:", data);
                // Handle the response or redirect to another page if needed
            })
            .catch((error) => {
                console.error("Error updating product:", error);
                // Handle errors if any
            });
    });
});