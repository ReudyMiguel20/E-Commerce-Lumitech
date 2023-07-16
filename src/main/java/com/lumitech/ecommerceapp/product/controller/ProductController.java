package com.lumitech.ecommerceapp.product.controller;

import com.lumitech.ecommerceapp.product.exception.ControllerExceptionHandler;
import com.lumitech.ecommerceapp.product.model.dto.ProductDTO;
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

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getSpecificProductById(@PathVariable Long id) {
        Product product = this.productService.findProductById(id);

        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/products")
    public ResponseEntity<?> storeNewProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product newProduct = this.productService.convertToProduct(productDTO);

        //Checking if the product exists and/or is null
        boolean doesProductExist = this.productService.doesProductExist(newProduct);
        boolean isProductNull = this.productService.isProductNull(newProduct);

        if (doesProductExist) {
            return ControllerExceptionHandler.handleProductExists();
        }

        if (isProductNull) {
            return ControllerExceptionHandler.handleProductEmptyOrNull();
        }

        //Add the product to the database and return a JSON response of said product
        this.productService.save(newProduct);
        return ResponseEntity.ok().body(newProduct);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateExistingProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO newProductInfo) {
        Product updatedProduct = this.productService.processUpdateProduct(id, newProductInfo);

        if (updatedProduct == null) {
            return ControllerExceptionHandler.handleProductEmptyOrNull();
        }

        return ResponseEntity.ok().body(updatedProduct);
    }
}
