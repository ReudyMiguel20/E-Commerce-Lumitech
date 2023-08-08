document.addEventListener("DOMContentLoaded", function () {
    const productForm = document.getElementById("product-form");

    const headers = {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + sessionStorage.getItem("token"),
    };

productForm.addEventListener("submit", function (event) {
    event.preventDefault();

    const formData = new FormData(productForm);
    const newProductData = Object.fromEntries(formData.entries());

    console.log(newProductData);

    fetch("http://localhost:9090/api/products", {
        method: "POST",
        headers: headers,
        body: JSON.stringify(newProductData),
    })
        .then((response) => response.json())
        .then((data) => {
            console.log("Success:", data);
            window.location.href = "/admin-dashboard";
        })
        .catch((error) => {
            console.error("Error:", error);
        });
});
});
