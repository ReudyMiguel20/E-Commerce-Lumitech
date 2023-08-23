const productList = document.getElementById("product-list");
const productGrid = document.getElementById("product-grid");
const sortSelect = document.getElementById("sort-select");

function fetchProducts(url) {
    fetch(url)
        .then((response) => response.json())
        .then((data) => {
            console.log(data);

            // Clear the product grid
            productGrid.innerHTML = "";

            for (const product of data) {
                const productItem = document.createElement("div");
                productItem.className = "grid-item";
                productItem.innerHTML = `
                    <div class="product">
                        <h3 id="${product.name}">${product.name}</h3>
                        
                        <div id = "single-product">

                        <div class="product-image">
                        <img src="${product.image}">
                        </div>
                        
                        <div class="product-info">
                        <p>Description: ${product.description}</p>
                        <p>Price: ${product.price}</p>
                        <p>Brand: ${product.brand}</p>
                        <p>Category: ${product.category}</p>
                        <p>Stock: ${product.stock}</p>
                        </div>
                        

                        <div class="product-button-quantity">
                        <label for="quantity">Quantity:</label>
                        <input type="number" id="quantity" name="quantity" min="1" max="100" value="1">
                        <button type="button" class="btn btn-success" data-product-name="${product.name}">Add to cart</button>
                        
                        </div>
                        
                    </div>
                  
                     </div>
                `;
                productGrid.appendChild(productItem);
            }


            const addToCartButtons = document.querySelectorAll(".btn-success");
            addToCartButtons.forEach((button) => {
                button.addEventListener("click", handleAddToCart);
            });
        });
}

// Fetch products on page load
fetchProducts("http://localhost:9090/api/products");

function handleAddToCart(event) {

    const productname = event.target.getAttribute("data-product-name");

    const quantityInput = event.target.parentElement.querySelector("input[type='number']");
    const quantity = quantityInput.value;

    const payload = {
        productname: productname, quantity: quantity,
    };

    const headers = {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("token"),
    }

    fetch("http://localhost:9090/api/cart", {
        method: "POST",
        headers: headers,
        body: JSON.stringify(payload),
    })
        .then((response) => response.json())
        .then((data) => {
            // Handle the response or update the UI if needed
            console.log(data);

            if (!data.error) {
                window.location.href = "/";
            } else {
                alert(data.message);
            }

        })
        .catch((error) => {
            // Handle any errors that occur during the POST request
            console.error("Error adding to cart:", error);
        });

}

sortSelect.addEventListener("change", function (event) {
    const selectedOption = event.target.value;

    const apiBaseUrl = 'http://localhost:9090/api/products';

    let apiSortUrl = apiBaseUrl;

    switch (selectedOption) {
        case 'nameAsc':
            apiSortUrl += '?sort=name&order=asc';
            break;
        case 'nameDesc':
            apiSortUrl += '?sort=name&order=desc';
            break;
        case 'priceAsc':
            apiSortUrl += '?sort=price&order=asc';
            break;
        case 'priceDesc':
            apiSortUrl += '?sort=price&order=desc';
            break;
        case 'categoryAsc':
            apiSortUrl += '?sort=category&order=asc';
            break;
        case 'categoryDesc':
            apiSortUrl += '?sort=category&order=desc';
            break;
        default:
            break;
    }

    // Fetch and display sorted products
    fetchProducts(apiSortUrl);
});

