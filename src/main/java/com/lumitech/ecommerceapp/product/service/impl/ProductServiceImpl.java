package com.lumitech.ecommerceapp.product.service.impl;

import com.lumitech.ecommerceapp.product.model.dto.NewProduct;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.repository.ProductRepository;
import com.lumitech.ecommerceapp.product.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public void addNewProduct(Product product){
        this.productRepository.save(product);
    }

    @Override
    public Product convertToProduct(NewProduct newProduct){
        return new Product(newProduct.getName(), newProduct.getDescription(), newProduct.getPrice(),
                newProduct.getCategory(), newProduct.getBrand());
    }

    @Override
    public boolean productExists(Product product){
        return this.productRepository.existsById(product.getId());
    }
}
