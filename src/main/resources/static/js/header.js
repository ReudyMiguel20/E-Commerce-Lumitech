document.addEventListener("DOMContentLoaded", function () {
    header = document.getElementById("header");
    header.innerHTML = `
    <nav class="navbar navbar-expand-lg navbar-light bg-info">
        <a class="navbar-brand" href="/">Lumitech</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown"
            aria-controls="navbarNavDropdown" aria-expanded="true" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="navbar-collapse collapse show" id="navbarNavDropdown" style="">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/products">Products</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/cart">Cart</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/orders">Orders</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin-dashboard">Admin Dashboard</a>
                </li>
                <li class="nav-item" id="register-link">
                    <a class="nav-link" href="/register">Register</a>
                </li>
                <li class="nav-item" id="login-link">
                    <a class="nav-link" href="/login">Login</a>
                </li>
            </ul>
        </div>
    </nav>
    `;

    const token = sessionStorage.getItem("token");
    if (token) {
        // User is logged in, hide the "Register" and "Login" links
        const registerLink = document.getElementById("register-link");
        const loginLink = document.getElementById("login-link");
        if (registerLink) {
            registerLink.style.display = "none";
        }
        if (loginLink) {
            loginLink.style.display = "none";
        }

        // Logout button
        const logoutButton = document.createElement("button");
        logoutButton.innerText = "Logout";
        logoutButton.className = "btn btn-danger ml-2";
        logoutButton.addEventListener("click", function () {
            // Remove the token from session storage
            sessionStorage.removeItem("token");
            // Redirect the user to the login page or perform other logout actions
            window.location.href = "/";

        });

        // Add the logout button to the navbar
        const navbarNav = header.querySelector(".navbar-nav");
        const logoutListItem = document.createElement("li");
        logoutListItem.className = "nav-item";
        logoutListItem.appendChild(logoutButton);
        navbarNav.appendChild(logoutListItem);
    }
});
