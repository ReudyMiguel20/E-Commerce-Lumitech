package com.lumitech.ecommerceapp.product.service.impl;

import com.lumitech.ecommerceapp.product.exception.errors.ProductDoesntExistsException;
import com.lumitech.ecommerceapp.product.exception.errors.ProductExistsException;
import com.lumitech.ecommerceapp.product.exception.errors.ProductsAreEqualsException;
import com.lumitech.ecommerceapp.product.exception.errors.UpdateValuesSameAsExistingProductException;
import com.lumitech.ecommerceapp.product.model.dto.ProductDTO;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.repository.ProductRepository;
import com.lumitech.ecommerceapp.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createNewProduct(ProductDTO productDTO) {
        Product newProduct = convertToProduct(productDTO);

        //Checking if the product exists and/or is null
        boolean doesProductExist = doesProductExist(newProduct);
        if (doesProductExist) {
            throw new ProductExistsException();
        }

        return saveAndReturnProduct(newProduct);
    }

    @Override
    public void saveProduct(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public Product saveAndReturnProduct(Product product) {
        return this.productRepository.save(product);
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
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ProductDoesntExistsException());
    }

    @Override
    public boolean doesProductExist(Product product) {
        return this.productRepository.findByName(product.getName()).isPresent();
    }

//    public void doesProductExist(Product product) {
//        if (this.productRepository.findByName(product.getName()).isEmpty()) {
//            throw new ProductDoesntExists();
//        }
//    }

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

    //This method doesn't work the same as the one in the Repository, just a reminder
    @Override
    public Product findByName(String productName) {
        return this.productRepository.findByName(productName)
                .orElseThrow(() -> new ProductDoesntExistsException());
    }

    @Override
    public Product processUpdateProduct(Long id, ProductDTO newProductInfo) {
        //Converting the DTO to a Product Object and sets its id, this is to compare if they are equal
        Product productWithNewInfo = convertToProduct(newProductInfo);
        productWithNewInfo.setId(id);

        //Find the product which is going to be updated
        Product productToUpdate = findProductById(id);

        if (productWithNewInfo.equals(productToUpdate)) {
           throw new ProductsAreEqualsException();
        }

        //Updating values
        Product updatedProduct = updateProductInfo(productToUpdate, productWithNewInfo);

        //Verify that a product with the same values doesn't exist before persisting the product to database
        verifyUpdatedProductExistence(updatedProduct);

        return updatedProduct;
    }

    @Override
    public Product updateProductInfo(Product productToUpdate, Product productWithNewInfo) {
        productToUpdate.setName(productWithNewInfo.getName());
        productToUpdate.setDescription(productWithNewInfo.getDescription());
        productToUpdate.setPrice(productWithNewInfo.getPrice());
        productToUpdate.setCategory(productWithNewInfo.getCategory());
        productToUpdate.setBrand(productWithNewInfo.getBrand());

        return productToUpdate;
    }

    public void verifyUpdatedProductExistence(Product updatedProduct) {
        //Looking in the database if a product with the same name exists
        Optional<Product> productWithSameName = this.productRepository.findByName(updatedProduct.getName());

        //Remember that above is going to return if it's empty. So the optional is going to handle if the product
        //is null as empty
        if (productWithSameName.isEmpty()) {
            saveProduct(updatedProduct);
        } else if (Objects.equals(productWithSameName.get(), updatedProduct)) {
            throw new UpdateValuesSameAsExistingProductException();
        }
    }
}
