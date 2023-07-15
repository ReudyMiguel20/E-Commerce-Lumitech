package com.lumitech.ecommerceapp.product.service;

import com.lumitech.ecommerceapp.product.model.dto.NewProduct;
import com.lumitech.ecommerceapp.product.model.entity.Product;

import java.util.List;


public interface ProductService {
    void save(Product product);
    Product convertToProduct(NewProduct newProduct);
    Product findProductById(Long id);
    boolean doesProductExist(Product product);
    boolean isProductNull(Product product);
    void deleteAll();
    List<Product> getAllProducts();
    Product findByName(String productName);
}
