package com.lumitech.ecommerceapp.product.service.impl;

import com.lumitech.ecommerceapp.product.model.dto.NewProduct;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.repository.ProductRepository;
import com.lumitech.ecommerceapp.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public Product convertToProduct(NewProduct newProduct) {
        return Product.builder()
                .name(newProduct.getName())
                .description(newProduct.getDescription())
                .price(newProduct.getPrice())
                .category(newProduct.getCategory())
                .brand(newProduct.getBrand())
                .build();
    }

    @Override
    public Product findProductById(Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    @Override
    public boolean doesProductExist(Product product) {
        for (Product productInList : getAllProducts()) {
            if (productInList.getName().equals(product.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isProductNull(Product product) {
        return product == null;
    }

    @Override
    public void deleteAll() {
        this.productRepository.deleteAll();
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product findByName(String productName) {
        return this.productRepository.findByName(productName)
                .orElse(null);
    }

//    public
}
