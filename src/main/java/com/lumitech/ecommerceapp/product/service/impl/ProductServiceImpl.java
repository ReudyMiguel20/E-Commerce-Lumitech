package com.lumitech.ecommerceapp.product.service.impl;

import com.lumitech.ecommerceapp.product.exception.errors.ProductsAreEquals;
import com.lumitech.ecommerceapp.product.model.dto.ProductDTO;
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
    public void save(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public Product convertToProduct(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .category(productDTO.getCategory())
                .brand(productDTO.getBrand())
                .build();
    }

    @Override
    public Product findProductById(Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    @Override
    public boolean doesProductExist(Product product) {
        return getAllProducts().stream()
                .anyMatch(productInList -> productInList.getName().equals(product.getName()));
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

    @Override
    public Product updateProductInfo(Long id, ProductDTO newProductInfo) {
        Product productWithNewInfo = convertToProduct(newProductInfo);
        productWithNewInfo.setId(id);

        Product productToUpdate = findProductById(id);

        if (productWithNewInfo.equals(productToUpdate)) {
           throw new ProductsAreEquals();
        }

        if (productToUpdate == null) {
            return null;
        }

        productToUpdate.setName(newProductInfo.getName());
        productToUpdate.setDescription(newProductInfo.getDescription());
        productToUpdate.setPrice(newProductInfo.getPrice());
        productToUpdate.setCategory(newProductInfo.getCategory());
        productToUpdate.setBrand(newProductInfo.getBrand());


        save(productToUpdate);
        return productToUpdate;
    }
}
