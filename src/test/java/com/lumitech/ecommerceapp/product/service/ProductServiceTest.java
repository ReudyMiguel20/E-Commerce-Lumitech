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
        Product product = new Product(
                "Test",
                "Test",
                1.0,
                "Test",
                "Test"
        );

        //Act
        productService.addNewProduct(product);

        //Assert
        Assertions.assertThat(product).isNotNull();
        assertEquals(product, productService.findProductById(product.getId()));
    }

    @Test
    void getAll_ReturnMoreThanOneProduct() {
        //Arrange
        Product product1 = new Product(
                "Test",
                "Test",
                1.0,
                "Test",
                "Test"
        );

        Product product2 = new Product(
                "Test",
                "Test",
                1.0,
                "Test",
                "Test"
        );

        //Act
        productService.addNewProduct(product1);
        productService.addNewProduct(product2);

        //Assert
        assertTrue(productService.getAllProducts().size() > 1);
        Assertions.assertThat(productService.getAllProducts()).isNotNull();
        Assertions.assertThat(productService.getAllProducts().size()).isEqualTo(2);
    }

    @Test
    void findProductById_ReturnProduct() {
        //Arrange
        Product product = new Product(
                "Test",
                "Test",
                1.0,
                "Test",
                "Test"
        );

        //Act
        productService.addNewProduct(product);

        //Assert
        Assertions.assertThat(productService.findProductById(product.getId())).isNotNull();
        Assertions.assertThat(productService.findProductById(product.getId())).isEqualTo(product);
    }

    @Test
    void deleteAll_DeletesAllProducts() {
        //Arrange
        Product product1 = new Product(
                "Test",
                "Test",
                1.0,
                "Test",
                "Test"
        );

        Product product2 = new Product(
                "Test",
                "Test",
                1.0,
                "Test",
                "Test"
        );

        //Act
        productService.addNewProduct(product1);
        productService.addNewProduct(product2);
        productService.deleteAll();

        //Assert
        Assertions.assertThat(productService.getAllProducts()).isNotNull();
        Assertions.assertThat(productService.getAllProducts().size()).isEqualTo(0);
    }

    @Test
    void verifyProductWithSameNameDoesNotGetAdded() {
        //Arrange
        Product product1 = new Product(
                "Test",
                "Test",
                1.0,
                "Test",
                "Test"
        );

        Product product2 = new Product(
                "A product",
                "Some Description",
                1.0,
                "Some category",
                "N/A"
        );

        this.productService.addNewProduct(product1);

        //Act
        boolean productWithSameNameExists = productService.doesProductExist(product2);

        this.productService.addNewProduct(product2);

        //Assert
        assertFalse(productWithSameNameExists);
        Assertions.assertThat(product2).isNotNull();
    }

    @Test
    void productPriceNotNegative() {
        Product product = new Product(
                "A product",
                "Some Description",
                1,
                "Some category",
                "N/A"
        );

        double price = product.getPrice();

        assertFalse(price < 0, "Price cannot be negative");
    }
}