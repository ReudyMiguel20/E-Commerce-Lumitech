package com.lumitech.ecommerceapp.product.service;

import com.lumitech.ecommerceapp.product.model.dto.NewProduct;
import com.lumitech.ecommerceapp.product.model.entity.Product;

public interface ProductService {
    void addNewProduct(Product product);
    Product convertToProduct(NewProduct newProduct);
    boolean productExists(Product product);
}
