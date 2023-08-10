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
                <a class="nav-link" href="/">Home </a>
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
            <li class="nav-item">
                <a class="nav-link" href="/login">Login</a>
            </li>
        </ul>
    </div>
</nav>
    `;

    const token = sessionStorage.getItem("token");
    if (token) {

    }



});