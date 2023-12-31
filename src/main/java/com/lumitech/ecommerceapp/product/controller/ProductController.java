package com.lumitech.ecommerceapp.product.controller;

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
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String sort,
                                                        @RequestParam(required = false) String order,
                                                        @RequestParam(required = false, defaultValue = "1") Integer page) {
        List<Product> productList = this.productService.getProducts(sort, order, page);
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getSpecificProductById(@PathVariable Long id) {
        Product product = this.productService.findProductById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> storeNewProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product newProduct = this.productService.createNewProduct(productDTO);
        return ResponseEntity.status(201).body(newProduct);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateExistingProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO newProductInfo) {
        Product updatedProduct = this.productService.processUpdateProduct(id, newProductInfo);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/products")
    public ResponseEntity<Product> deleteProductByName(@RequestParam(value = "name") String productNameToDelete) {
        Product productToDelete = productService.findByNameIgnoreCase(productNameToDelete);
        productService.deleteProduct(productToDelete);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable long id) {
        Product productToDelete = productService.findProductById(id);
        productService.deleteProduct(productToDelete);
        return ResponseEntity.noContent().build();
    }
}


