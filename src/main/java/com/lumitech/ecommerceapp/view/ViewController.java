package com.lumitech.ecommerceapp.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String getIndexPage() {
        return "html/index.html";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "html/register.html";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "html/login.html";
    }

    @GetMapping("/cart")
    public String getCartPage() {
        return "html/usercart.html";
    }

    @GetMapping("/admin-dashboard")
    public String getAdminDashboard() {
        return "html/admin-dashboard.html";
    }

    @GetMapping("/update-product")
    public String getUpdateProductPage() {
        return "html/update-product.html";
    }

    @GetMapping("/add-product")
    public String getAddNewProductPage() {
        return "html/add-new-product.html";
    }

    @GetMapping("/products")
    public String getProductPage() {
        return "html/products.html";
    }

    @GetMapping("/orders")
    public String getOrdersPage() {
        return "html/orders.html";
    }
}
