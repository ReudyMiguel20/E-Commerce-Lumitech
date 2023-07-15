package com.lumitech.ecommerceapp.product.controller;

import com.lumitech.ecommerceapp.product.exception.ControllerExceptionHandler;
import com.lumitech.ecommerceapp.product.exception.errors.ProductExistsException;
import com.lumitech.ecommerceapp.product.model.dto.NewProduct;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


//    @GetMapping("/all")
//    public ResponseEntity<List<Product>> getAllProducts() {
//
//    }


    @PostMapping("/new")
    public ResponseEntity<?> storeNewProduct(@Valid @RequestBody NewProduct newProduct) {
        // I have a problem here, I don't know how to check if the item already exists on the database, maybe check name, maybe multiple fields, idk.
        Product tempProduct = this.productService.convertToProduct(newProduct);

        //Checking if the product exists on the database
        this.productService.productExists(tempProduct);

        // Return an exception here
        if (tempProduct == null) {
            return ControllerExceptionHandler.handleProductExists();
        } else {
            this.productService.addNewProduct(tempProduct);
            return ResponseEntity.ok().body(tempProduct);
        }
    }


}
