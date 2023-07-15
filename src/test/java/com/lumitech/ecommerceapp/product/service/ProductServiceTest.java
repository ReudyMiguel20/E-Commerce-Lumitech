package com.lumitech.ecommerceapp.product.service;

import com.lumitech.ecommerceapp.product.model.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @AfterEach
    void tearDown() {
        productService.deleteAll();
    }

    @Test
    void saveNewProduct_SuccessfullySavesProduct() {
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
        Assertions.assertThat(product).isNotNull();
        assertEquals(product, productService.findProductById(product.getId()));
    }

    @Test
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
        assertTrue(productService.getAllProducts().size() > 1);
        Assertions.assertThat(productService.getAllProducts()).isNotNull();
        Assertions.assertThat(productService.getAllProducts().size()).isEqualTo(2);
    }

    @Test
    void findProductById_ReturnProduct() {
        //Arrange
        Product product = Product.builder()
                .name("Test")
                .build();

        //Act
        productService.save(product);

        //Assert
        Assertions.assertThat(productService.findProductById(product.getId())).isNotNull();
        Assertions.assertThat(productService.findProductById(product.getId())).isEqualTo(product);
    }

    @Test
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
        Assertions.assertThat(productService.getAllProducts()).isNotNull();
        Assertions.assertThat(productService.getAllProducts().size()).isEqualTo(0);
    }

    @Test
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
        assertFalse(productWithSameNameExists);
        Assertions.assertThat(product2).isNotNull();
    }

    @Test
    void productPriceNotNegative() {
        //Arrange
        Product product = Product.builder()
                .price(1)
                .build();

        //Act
        double price = product.getPrice();

        //Assert
        assertFalse(price < 0, "Price cannot be negative");
    }

    @Test
    void getProductByNameNotNull() {
        //Arrange
        Product product = Product.builder()
                .name("A Product")
                .build();

        productService.save(product);

        //Act
        Product tempProduct = productService.findByName("A Product"); //Object appears to be null here, why?

        //Assert
        Assertions.assertThat(tempProduct).isNotNull();
        Assertions.assertThat(tempProduct.getId()).isEqualTo(product.getId());
        Assertions.assertThat(tempProduct).isEqualTo(product);
    }
}