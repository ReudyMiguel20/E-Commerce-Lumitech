package com.lumitech.ecommerceapp.product.controller;

import com.lumitech.ecommerceapp.product.exception.ControllerExceptionHandler;
import com.lumitech.ecommerceapp.product.model.dto.NewProduct;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok().body(this.productService.getAllProducts());
    }


    @PostMapping("/products")
    public ResponseEntity<?> storeNewProduct(@Valid @RequestBody NewProduct newProduct) {
        Product tempProduct = this.productService.convertToProduct(newProduct);

        //Checking if the product exists and/or is null
        boolean doesProductExist = this.productService.doesProductExist(tempProduct);
        boolean isProductNull = this.productService.isProductNull(tempProduct);

        if (doesProductExist) {
            return ControllerExceptionHandler.handleProductExists();
        }

        if (isProductNull) {
            return ControllerExceptionHandler.handleProductEmptyOrNull();
        }

        //Add the product to the database and return a JSON response of said product
        this.productService.addNewProduct(tempProduct);
        return ResponseEntity.ok().body(tempProduct);
    }
}
