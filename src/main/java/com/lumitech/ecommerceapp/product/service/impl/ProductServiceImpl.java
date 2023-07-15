package com.lumitech.ecommerceapp.product.service.impl;

import com.lumitech.ecommerceapp.product.model.dto.NewProduct;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.repository.ProductRepository;
import com.lumitech.ecommerceapp.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addNewProduct(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public Product convertToProduct(NewProduct newProduct) {
        return new Product(newProduct.getName(), newProduct.getDescription(), newProduct.getPrice(), newProduct.getCategory(), newProduct.getBrand());
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
        if (product == null) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        this.productRepository.deleteAll();
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

//    public
}
