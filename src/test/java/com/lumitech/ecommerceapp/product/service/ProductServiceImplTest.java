package com.lumitech.ecommerceapp.product.service;

import com.lumitech.ecommerceapp.product.exception.errors.ProductsAreEquals;
import com.lumitech.ecommerceapp.product.model.dto.ProductDTO;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.service.impl.ProductServiceImpl;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @AfterEach
    void tearDown() {
        productService.deleteAll();
    }

    @Test
    @DisplayName("Saving new product should be saved and not null")
    void saveNewProduct_SuccessfullySavesProductAndIsNotNull() {
        //Arrange
        Product product = Product.builder()
                .name("Test")
                .description("Test")
                .price(1.0)
                .category("Test")
                .brand("Test")
                .build();

        //Act
        productService.save(product);

        //Assert
        Assertions.assertThat(product)
                .as("Product is null")
                .isNotNull();

        Assertions.assertThat(productService.findProductById(product.getId()))
                .as("Product is not saved")
                .isEqualTo(product);
    }

    @Test
    @DisplayName("Add two products, should return both products")
    void getAll_ReturnMoreThanOneProduct() {
        //Arrange
        Product product1 = Product.builder()
                .name("Test1")
                .build();

        Product product2 = Product.builder()
                .name("Test2")
                .build();

        //Act
        productService.save(product1);
        productService.save(product2);

        //Assert
        Assertions.assertThat(productService.getAllProducts().size()).as("Does not contain the correct number of products").isGreaterThan(1);
        Assertions.assertThat(productService.getAllProducts()).as("The list of products is null").isNotNull();
    }

    @Test
    @DisplayName("Find product by id, should return product")
    void findProductById_ReturnProduct() {
        //Arrange
        Product product = Product.builder()
                .name("Test")
                .build();

        //Act
        productService.save(product);

        //Assert
        Assertions.assertThat(productService.findProductById(product.getId())).as("Product is null").isNotNull();
        Assertions.assertThat(productService.findProductById(product.getId())).as("Product is not the same").isEqualTo(product);
    }

    @Test
    @DisplayName("Deleting all products, should delete all products")
    void deleteAll_DeletesAllProducts() {
        //Arrange
        Product product1 = Product.builder()
                .name("product1")
                .build();

        Product product2 = Product.builder()
                .name("product2")
                .build();

        //Act
        productService.save(product1);
        productService.save(product2);
        productService.deleteAll();

        //Assert
        Assertions.assertThat(productService.getAllProducts())
                .as("List of products is null")
                .isNotNull();

        Assertions.assertThat(productService.getAllProducts().size())
                .as("List of products is not empty")
                .isEqualTo(0);
    }

    @Test
    @DisplayName("Product with same name should not be added")
    void verifyProductWithSameNameDoesNotGetAdded() {
        //Arrange
        Product product1 = Product.builder()
                .name("product1")
                .build();

        Product product2 = Product.builder()
                .name("product2")
                .build();

        this.productService.save(product1);

        //Act
        boolean productWithSameNameExists = productService.doesProductExist(product2);

        this.productService.save(product2);

        //Assert
        Assertions.assertThat(productWithSameNameExists)
                .as("Product with same name exists")
                .isEqualTo(false);

        Assertions.assertThat(product2)
                .as("Product is null")
                .isNotNull();
    }

    @Test
    @DisplayName("Product should not be negative")
    void productPriceNotNegative() {
        //Arrange
        Product product = Product.builder()
                .price(1)
                .build();

        //Act
        double price = product.getPrice();

        //Assert
        Assertions.assertThat(price)
                .as("The price is negative")
                .isGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("Finding a product by name, should return product asked for")
    void getProductByNameNotNull() {
        //Arrange
        Product product = Product.builder()
                .name("A Product")
                .build();

        productService.save(product);

        //Act
        Product tempProduct = productService.findByName("A Product");

        //Assert
        Assertions.assertThat(tempProduct).isNotNull();
        Assertions.assertThat(tempProduct.getId()).isEqualTo(product.getId());
        Assertions.assertThat(tempProduct).isEqualTo(product);
    }

    @Test
    void updateProductBySpecifyingId() {
        //Arrange
        Product productOne = Product.builder()
                .name("product")
                .description("test")
                .price(1)
                .category("test")
                .brand("test")
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .name("product")
                .description("test1")
                .price(1)
                .category("test")
                .brand("test")
                .build();

        productService.save(productOne);

        //Act
        Product productTwo = productService.updateProductInfo(productOne.getId(), productDTO);

        //Assert
        Assertions.assertThat(productTwo)
                .as("Product wasn't updated, still the same")
                .isNotEqualTo(productOne);

        Assertions.assertThat(productTwo)
                .as("Product is null")
                .isNotNull();

        Assertions.assertThat(productTwo.getPrice())
                .as("Product cannot be negative")
                .isGreaterThanOrEqualTo(1L);
    }
}