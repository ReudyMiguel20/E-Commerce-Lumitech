// document.addEventListener("DOMContentLoaded", function () {
//     const footer = document.getElementById("footer");
//     footer.innerHTML = `
//     <footer class="footer">
//     <div class="container">
//         <div class="row">
//             <div class="col-md-4">
//                 <h5>Information</h5>
//                 <ul>
//                     <li><a href="/">Home</a></li>
//                     <li><a href="/products">Products</a></li>
//                     <li><a href="/cart">Cart</a></li>
//                     <li><a href="/orders">Orders</a></li>
//                     <li><a href="/admin-dashboard">Admin Dashboard</a></li>
//                     <li><a href="/login">Login</a></li>
//                 </ul>
//             </div>
//             <div class="col-md-4">
//                 <h5>My Account</h5>
//                 <ul>
//                     <li><a href="/login">Login</a></li>
//                     <li><a href="/register">Register</a></li>
//                 </ul>
//             </div>
//             <div class="col-md-4">
//                 <h5>Contact Us</h5>
//                 <ul>
//                     <li><a href="https://www.facebook.com/lumitech.vn">Facebook</a></li>
//                     <li><a href="https://www.instagram.com/lumitech.vn/">Instagram</a></li>
//                     <li>
//                         <a href="https://www.youtube.com/channel/UC4QX0Z0Z6Q8J5QZ1Z6Z6Z6Q">Youtube</a>
//                     </li>
//                     <li><a href="https://www.linkedin.com/company/lumitech-vn">Linkedin</a></li>
//                 </ul>
//             </div>
//         </div>
//     </div>
// </footer>
//     `;
//
//     const token = sessionStorage.getItem("token");
//     if (token) {
//
//     }
//
//     const logoutButton = document.getElementById("logout-button");
//     if (logoutButton) {
// logoutButton.addEventListener("click", function () {
//             sessionStorage.removeItem("token");
//             window.location.href = "/login";
//         });
//     }
//
//     const loginButton = document.getElementById("login-button");
//     if (loginButton) {
// loginButton.addEventListener("click", function () {
//             window.location.href = "/login";
//         });
//     }
//
//     const registerButton = document.getElementById("register-button");
//     if (registerButton) {
//
//     }
//
//     const cartButton = document.getElementById("cart-button");
//     if (cartButton) {
//
//     }
//
// });
