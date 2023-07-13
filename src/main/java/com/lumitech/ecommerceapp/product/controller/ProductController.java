package com.lumitech.ecommerceapp.product.controller;

import com.lumitech.ecommerceapp.common.exception.ControllerExceptionHandler;
import com.lumitech.ecommerceapp.product.model.dto.NewProduct;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> storeNewProduct(@Valid @RequestBody NewProduct newProduct){
        Product tempProduct = this.productService.convertToProduct(newProduct);
        boolean productExists = this.productService.productExists(tempProduct);


        if (tempProduct == null) {
            return ResponseEntity.badRequest().body("Invalid product data");
        } else if (productExists) {
           return ControllerExceptionHandler.handleItemExists();
        } else {
            this.productService.addNewProduct(tempProduct);
            return ResponseEntity.ok().body(tempProduct);
        }
    }


}
