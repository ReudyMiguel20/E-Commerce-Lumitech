package com.lumitech.ecommerceapp.product.service.impl;

import com.lumitech.ecommerceapp.product.exception.errors.*;
import com.lumitech.ecommerceapp.product.model.dto.ProductDTO;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.repository.ProductRepository;
import com.lumitech.ecommerceapp.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
        return this.productRepository.findByNameIgnoreCase(product.getName()).isPresent();
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
    public List<Product> getProducts(String sort, String order, int page) {
        //Sort by id by default, this is always the case if 'sort' parameter is null
        Sort sortOptions = Sort.by("id");

        if (sort != null) {
            sortOptions = Sort.by(sort);
        }

        if (order != null) {
            if (order.equalsIgnoreCase("desc")) {
                sortOptions = sortOptions.descending();
            } else if (order.equalsIgnoreCase("asc")) {
                sortOptions = sortOptions.ascending();
            }
        }

        //Making sure that page is never less than 0
        page = Math.max(page - 1, 0);

        //Handle exception if 'sort' parameter is not valid
        try {
            Pageable pageable = PageRequest.of(page, 10, sortOptions);
            return this.productRepository.findAll(pageable).getContent();
        } catch (PropertyReferenceException e) {
            throw new ProductSortingException();
        }
    }

    //This method doesn't work the same as the one in the Repository, just a reminder
    @Override
    public Product findByNameIgnoreCase(String productName) {
        return this.productRepository.findByNameIgnoreCase(productName)
                .orElseThrow(() -> new ProductDoesntExistsException());
    }

    /**
     * Processes the update of a product with the provided information.
     *
     * @param id             The ID of the product to update.
     * @param newProductInfo The updated product information as a DTO.
     * @return updatedProduct The updated product after the update process.
     * @throws ProductsAreEqualsException if the updated product information is equal to the existing product.
     */
    @Override
    public Product processUpdateProduct(Long id, ProductDTO newProductInfo) {
        //Converting the DTO to a Product Object and sets its id, this is to compare if they are equal
        Product productWithNewInfo = convertToProduct(newProductInfo);
        productWithNewInfo.setId(id);

        Product productToUpdate = findProductById(id);

        if (Objects.equals(productWithNewInfo, productToUpdate)) {
            throw new ProductsAreEqualsException();
        }

        //Updating values
        Product updatedProduct = updateProductInfo(productToUpdate, productWithNewInfo);

        //Verify that a product with the same values doesn't exist before persisting the product to database
        verifyUpdatedProductExistence(updatedProduct);

        return updatedProduct;
    }

    /**
     * Updates the information of a product with new information.
     *
     * @param productToUpdate    - Product which is going to be updated (Old info)
     * @param productWithNewInfo - Product with the updated info (New Info)
     * @return productToUpdate - returns the old product updated with the new info
     */
    @Override
    public Product updateProductInfo(Product productToUpdate, Product productWithNewInfo) {
        productToUpdate.setName(productWithNewInfo.getName());
        productToUpdate.setDescription(productWithNewInfo.getDescription());
        productToUpdate.setPrice(productWithNewInfo.getPrice());
        productToUpdate.setCategory(productWithNewInfo.getCategory());
        productToUpdate.setBrand(productWithNewInfo.getBrand());

        return productToUpdate;
    }

    @Override
    public void verifyUpdatedProductExistence(Product updatedProduct) {
        //Looking in the database if a product with the same name exists
        Optional<Product> productWithSameName = this.productRepository.findByNameIgnoreCase(updatedProduct.getName());

        /* Remember that above is going to return if it's empty. So the optional is going to handle
           if the product is null as empty */
        if (productWithSameName.isEmpty()) {
            saveProduct(updatedProduct);
        } else if (Objects.equals(productWithSameName.get(), updatedProduct)) {
            throw new UpdateValuesSameAsExistingProductException();
        }
    }

    @Override
    public void deleteProduct(Product productToBeDeleted) {
        this.productRepository.delete(productToBeDeleted);
    }

    @Override
    public void deleteProductById(Long idProductToBeDeleted) {
        this.productRepository.deleteById(idProductToBeDeleted);
    }
}
