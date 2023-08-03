const productList = document.getElementById("product-list");

function fetchProducts() {
    fetch("http://localhost:9090/api/products")
        .then((response) => response.json())
        .then((data) => {
            console.log(data);

            for (const product of data) {
                const productItem = document.createElement("li");
                productItem.innerHTML = `
                    <div class="product">
                        <h3>${product.name}</h3>
                        <p>${product.price}</p>
                        <p>${product.image}</p>
                    </div>
                `;
                productList.appendChild(productItem);
            }
        });
}

fetchProducts();