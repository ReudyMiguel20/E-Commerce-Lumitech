package com.lumitech.ecommerceapp.product.service;

import com.lumitech.ecommerceapp.product.model.dto.ProductDTO;
import com.lumitech.ecommerceapp.product.model.entity.Product;

import java.util.List;


public interface ProductService {
    void saveProduct(Product product);

    Product saveAndReturnProduct(Product product);

    Product convertToProduct(ProductDTO productDTO);

    Product findProductById(Long id);

    boolean doesProductExist(Product product);

    boolean isProductNull(Product product);

    void deleteAll();

    List<Product> getAllProducts();

    Product findByName(String productName);

    Product processUpdateProduct(Long id, ProductDTO newProductInfo);

    Product updateProductInfo(Product oldProduct, Product newProduct);
}
