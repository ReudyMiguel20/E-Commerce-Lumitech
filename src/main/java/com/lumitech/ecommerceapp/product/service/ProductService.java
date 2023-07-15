package com.lumitech.ecommerceapp.product.service;

import com.lumitech.ecommerceapp.product.model.dto.NewProduct;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    void addNewProduct(Product product);
    Product convertToProduct(NewProduct newProduct);
    Product findProductById(Long id);
    boolean productWithSameNameExists(Product product);
    boolean productExists(Product product);
    void deleteAll();
    List<Product> getAllProducts();
}
