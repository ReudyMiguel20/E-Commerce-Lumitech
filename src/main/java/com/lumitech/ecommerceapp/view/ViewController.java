package com.lumitech.ecommerceapp.view;

import jakarta.annotation.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ViewController {

    @GetMapping("/")
    public String getIndexPage() {
        return "html/index.html";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "html/login.html";
    }
}
