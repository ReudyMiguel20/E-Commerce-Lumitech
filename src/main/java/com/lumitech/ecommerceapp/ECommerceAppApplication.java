package com.lumitech.ecommerceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.lumitech.ecommerceapp",  // Include your root package
        "com.lumitech.ecommerceapp.auth.service",
        "com.lumitech.ecommerceapp.users.repository"// Include the package containing AuthService
})
public class ECommerceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceAppApplication.class, args);
    }

}
