package com.lumitech.ecommerceapp.product.service;

import com.lumitech.ecommerceapp.product.model.dto.ProductDTO;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.service.impl.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
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
        productService.saveProduct(product);

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
        productService.saveProduct(product1);
        productService.saveProduct(product2);

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
        productService.saveProduct(product);

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
        productService.saveProduct(product1);
        productService.saveProduct(product2);
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
                .name("Test")
                .description("Test")
                .price(1.0)
                .category("Test")
                .brand("Test")
                .build();

        Product product2 = Product.builder()
                .name("Test2")
                .description("Tes2")
                .price(1.0)
                .category("Test2")
                .brand("Test2")
                .build();

        this.productService.saveProduct(product1);

        //Act
        boolean productWithSameNameExists = productService.doesProductExist(product2);

        this.productService.saveProduct(product2);

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

        productService.saveProduct(product);

        //Act
        Product tempProduct = productService.findByNameIgnoreCase("A Product");

        //Assert
        Assertions.assertThat(tempProduct).isNotNull();
        Assertions.assertThat(tempProduct.getId()).isEqualTo(product.getId());
        Assertions.assertThat(tempProduct).isEqualTo(product);
    }

    //Write a name for this test
    @Test
    @DisplayName("Update a product by specifying the ID")
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

        productService.saveProduct(productOne);

        //Act
        Product productTwo = productService.processUpdateProduct(productOne.getId(), productDTO);

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

    @Test
    @DisplayName("Deleting a product by name")
    void deletingProductByName() {
        //Arrange
        Product productOne = Product.builder()
                .name("samsung 10")
                .description("test")
                .price(1)
                .category("test")
                .brand("test")
                .build();

        Product productTwo = Product.builder()
                .name("dont delete me")
                .description("test")
                .price(1)
                .category("test")
                .brand("test")
                .build();
        productService.saveProduct(productOne);
        productService.saveProduct(productTwo);

        //Checking if the product exists by giving a name
        String productNameToDelete = "Samsung 10";
        Product productToDelete = productService.findByNameIgnoreCase(productNameToDelete);

        //Act
        productService.deleteProduct(productToDelete);

        //Assert
        Assertions.assertThat(productNameToDelete.toLowerCase())
                .as("The product to delete doesn't match any of the existing products")
                .isEqualTo(productToDelete.getName().toLowerCase());

        Assertions.assertThat(productToDelete)
                .as("Product is null")
                .isNotNull();

        Assertions.assertThat(productService.getAllProducts())
                .as("Product wasn't deleted from the list")
                .doesNotContain(productToDelete);

        Assertions.assertThat(productService.getAllProducts())
                .as("The list shouldn't be null, there should be values on the list")
                .isNotNull();

        Assertions.assertThat(productService.getAllProducts())
                .as("The list shouldn't be empty, there should be products on the list")
                .isNotEmpty();
    }

    @Test
    @DisplayName("Deleting a product by ID")
    void deleteProductById() {
        //Arrange
        Product productOne = Product.builder()
                .name("samsung 10")
                .description("test")
                .price(1)
                .category("test")
                .brand("test")
                .build();

        Product productTwo = Product.builder()
                .name("dont delete me")
                .description("test")
                .price(1)
                .category("test")
                .brand("test")
                .build();
        productService.saveProduct(productOne);
        productService.saveProduct(productTwo);

        Product productToDelete = productService.findProductById(productOne.getId());

        //Act
        productService.deleteProduct(productToDelete);

        Assertions.assertThat(productOne.getName().toLowerCase())
                .as("The product to delete doesn't match any of the existing products")
                .isEqualTo(productToDelete.getName().toLowerCase());

        Assertions.assertThat(productToDelete)
                .as("Product is null")
                .isNotNull();

        Assertions.assertThat(productService.getAllProducts())
                .as("Product wasn't deleted from the list")
                .doesNotContain(productToDelete);

        Assertions.assertThat(productService.getAllProducts())
                .as("The list shouldn't be null, there should be values on the list")
                .isNotNull();

        Assertions.assertThat(productService.getAllProducts())
                .as("The list shouldn't be empty, there should be products on the list")
                .isNotEmpty();
    }

    @Test
    @DisplayName("Sorting products by Price ascending")
    void getListOfProductsSortedByPriceAsc() {
        //Arrange
        Product productOne = Product.builder()
                .name("test")
                .description("test")
                .price(45.32)
                .category("test")
                .brand("test")
                .build();

        Product productTwo = Product.builder()
                .name("test")
                .description("test")
                .price(100.23)
                .category("test")
                .brand("test")
                .build();

        Product productThree = Product.builder()
                .name("test")
                .description("test")
                .price(56.00)
                .category("test")
                .brand("test")
                .build();
        productService.saveProduct(productOne);
        productService.saveProduct(productTwo);
        productService.saveProduct(productThree);

        //Act
        Iterable<Product> iterableProductList = productService.getProducts("price", "asc");

        //Creating and converting the Iterable to a Collection
        List<Product> productList = new ArrayList<>();
        iterableProductList.forEach(productList::add);

        //Assert
        Assertions.assertThat(productList.size())
                .as("List size is expected to be 3")
                .isEqualTo(3);

        Assertions.assertThat(productList.get(0))
                .as("The product with the lowest price is not first")
                .isEqualTo(productOne);

        Assertions.assertThat(productList.get(2))
                .as("The product with the highest price is not last")
                .isEqualTo(productTwo);
    }

    @Test
    @DisplayName("Sorting products by Price descending")
    void getListOfProductsSortedByPriceDesc() {
        //Arrange
        Product productOne = Product.builder()
                .name("test")
                .description("test")
                .price(45.32)
                .category("test")
                .brand("test")
                .build();

        Product productTwo = Product.builder()
                .name("test")
                .description("test")
                .price(100.23)
                .category("test")
                .brand("test")
                .build();

        Product productThree = Product.builder()
                .name("test")
                .description("test")
                .price(56.00)
                .category("test")
                .brand("test")
                .build();
        productService.saveProduct(productOne);
        productService.saveProduct(productTwo);
        productService.saveProduct(productThree);

        //Act
        Iterable<Product> iterableProductList = productService.getProducts("price", "desc");

        //Creating and converting the Iterable to a Collection
        List<Product> productList = new ArrayList<>();
        iterableProductList.forEach(productList::add);

        //Assert
        Assertions.assertThat(productList.size())
                .as("List size is expected to be 3")
                .isEqualTo(3);

        Assertions.assertThat(productList.get(0))
                .as("The product with the highest price is not first")
                .isEqualTo(productTwo);

        Assertions.assertThat(productList.get(2))
                .as("The product with the lowest price is not last")
                .isEqualTo(productOne);
    }

    @Test
    @DisplayName("Sorting products by Name ascending")
    void getListOfProductsSortedByNameAsc() {
        //Arrange
        Product productOne = Product.builder()
                .name("Zerox Keyboard")
                .description("test")
                .price(45.32)
                .category("test")
                .brand("test")
                .build();

        Product productTwo = Product.builder()
                .name("ASUS Motherboard")
                .description("test")
                .price(100.23)
                .category("test")
                .brand("test")
                .build();

        Product productThree = Product.builder()
                .name("Logitech Mouse G22")
                .description("test")
                .price(56.00)
                .category("test")
                .brand("test")
                .build();
        productService.saveProduct(productOne);
        productService.saveProduct(productTwo);
        productService.saveProduct(productThree);

        //Act
        Iterable<Product> iterableProductList = productService.getProducts("name", "asc");

        //Creating and converting the Iterable to a Collection
        List<Product> productList = new ArrayList<>();
        iterableProductList.forEach(productList::add);

        //Assert
        Assertions.assertThat(productList.size())
                .as("List size is expected to be 3")
                .isEqualTo(3);

        Assertions.assertThat(productList.get(0))
                .as("The first product should be " + productTwo.getName())
                .isEqualTo(productTwo);

        Assertions.assertThat(productList.get(2))
                .as("The last product should be " + productTwo.getName())
                .isEqualTo(productOne);
    }

    @Test
    @DisplayName("Sorting products by Name descending")
    void getListOfProductsSortedByNameDesc() {
        //Arrange
        Product productOne = Product.builder()
                .name("Zerox Keyboard")
                .description("test")
                .price(45.32)
                .category("test")
                .brand("test")
                .build();

        Product productTwo = Product.builder()
                .name("ASUS Motherboard")
                .description("test")
                .price(100.23)
                .category("test")
                .brand("test")
                .build();

        Product productThree = Product.builder()
                .name("Logitech Mouse G22")
                .description("test")
                .price(56.00)
                .category("test")
                .brand("test")
                .build();
        productService.saveProduct(productOne);
        productService.saveProduct(productTwo);
        productService.saveProduct(productThree);

        //Act
        Iterable<Product> iterableProductList = productService.getProducts("name", "desc");

        //Creating and converting the Iterable to a Collection
        List<Product> productList = new ArrayList<>();
        iterableProductList.forEach(productList::add);

        //Assert
        Assertions.assertThat(productList.size())
                .as("List size is expected to be 3")
                .isEqualTo(3);

        Assertions.assertThat(productList.get(0))
                .as("The first product should be " + productOne.getName())
                .isEqualTo(productOne);

        Assertions.assertThat(productList.get(2))
                .as("The last product should be " + productTwo.getName())
                .isEqualTo(productTwo);
    }

    @Test
    @DisplayName("Sorting products by Brand ascending")
    void getListOfProductsSortedByBrandAsc() {
        //Arrange
        Product productOne = Product.builder()
                .name("Logitech Mouse G22")
                .description("test")
                .price(56.00)
                .category("test")
                .brand("logitech")
                .build();

        Product productTwo = Product.builder()
                .name("Zerox Keyboard")
                .description("test")
                .price(45.32)
                .category("test")
                .brand("zerox")
                .build();

        Product productThree = Product.builder()
                .name("ASUS Motherboard")
                .description("test")
                .price(100.23)
                .category("test")
                .brand("asus")
                .build();
        productService.saveProduct(productOne);
        productService.saveProduct(productTwo);
        productService.saveProduct(productThree);

        //Act
        Iterable<Product> iterableProductList = productService.getProducts("brand", "asc");

        //Creating and converting the Iterable to a Collection
        List<Product> productList = new ArrayList<>();
        iterableProductList.forEach(productList::add);

        //Assert
        Assertions.assertThat(productList.size())
                .as("List size is expected to be 3")
                .isEqualTo(3);

        Assertions.assertThat(productList.get(0))
                .as("The first product category should be " + productThree.getBrand())
                .isEqualTo(productThree);

        Assertions.assertThat(productList.get(2))
                .as("The last product category should be " + productTwo.getBrand())
                .isEqualTo(productTwo);
    }

    @Test
    @DisplayName("Sorting products by Brand descending")
    void getListOfProductsSortedByBrandDesc() {
        //Arrange
        Product productOne = Product.builder()
                .name("Logitech Mouse G22")
                .description("test")
                .price(56.00)
                .category("test")
                .brand("logitech")
                .build();

        Product productTwo = Product.builder()
                .name("Zerox Keyboard")
                .description("test")
                .price(45.32)
                .category("test")
                .brand("zerox")
                .build();

        Product productThree = Product.builder()
                .name("ASUS Motherboard")
                .description("test")
                .price(100.23)
                .category("test")
                .brand("asus")
                .build();
        productService.saveProduct(productOne);
        productService.saveProduct(productTwo);
        productService.saveProduct(productThree);

        //Act
        Iterable<Product> iterableProductList = productService.getProducts("brand", "desc");

        //Creating and converting the Iterable to a Collection
        List<Product> productList = new ArrayList<>();
        iterableProductList.forEach(productList::add);

        //Assert
        Assertions.assertThat(productList.size())
                .as("List size is expected to be 3")
                .isEqualTo(3);

        Assertions.assertThat(productList.get(0))
                .as("The first product category should be " + productTwo.getBrand())
                .isEqualTo(productTwo);

        Assertions.assertThat(productList.get(2))
                .as("The last product category should be " + productThree.getBrand())
                .isEqualTo(productThree);
    }

    @Test
    @DisplayName("Sorting products by Category ascending")
    void getListOfProductsSortedByCategoryAsc() {
        //Arrange
        Product productOne = Product.builder()
                .name("Logitech Mouse G22")
                .description("test")
                .price(56.00)
                .category("Accessory")
                .brand("logitech")
                .build();

        Product productTwo = Product.builder()
                .name("Zerox Keyboard")
                .description("test")
                .price(45.32)
                .category("")
                .brand("Accessory")
                .build();

        Product productThree = Product.builder()
                .name("ASUS Motherboard")
                .description("PC Component")
                .price(100.23)
                .category("PC Component")
                .brand("asus")
                .build();
        productService.saveProduct(productOne);
        productService.saveProduct(productTwo);
        productService.saveProduct(productThree);

        //Act
        Iterable<Product> iterableProductList = productService.getProducts("category", "asc");

        //Creating and converting the Iterable to a Collection
        List<Product> productList = new ArrayList<>();
        iterableProductList.forEach(productList::add);

        //Assert
        Assertions.assertThat(productList.size())
                .as("List size is expected to be 3")
                .isEqualTo(3);

        Assertions.assertThat(productList.get(0))
                .as("The first product category should be " + productTwo.getCategory())
                .isEqualTo(productTwo);

        Assertions.assertThat(productList.get(2))
                .as("The first product category should be " + productThree.getCategory())
                .isEqualTo(productThree);
    }

    @Test
    @DisplayName("Sorting products by Category descending")
    void getListOfProductsSortedByCategoryDesc() {
        //Arrange
        Product productOne = Product.builder()
                .name("Logitech Mouse G22")
                .description("test")
                .price(56.00)
                .category("Accessory")
                .brand("logitech")
                .build();

        Product productTwo = Product.builder()
                .name("Zerox Keyboard")
                .description("test")
                .price(45.32)
                .category("")
                .brand("Accessory")
                .build();

        Product productThree = Product.builder()
                .name("ASUS Motherboard")
                .description("PC Component")
                .price(100.23)
                .category("PC Component")
                .brand("asus")
                .build();
        productService.saveProduct(productOne);
        productService.saveProduct(productTwo);
        productService.saveProduct(productThree);

        //Act
        Iterable<Product> iterableProductList = productService.getProducts("category", "desc");

        //Creating and converting the Iterable to a Collection
        List<Product> productList = new ArrayList<>();
        iterableProductList.forEach(productList::add);

        //Assert
        Assertions.assertThat(productList.size())
                .as("List size is expected to be 3")
                .isEqualTo(3);

        Assertions.assertThat(productList.get(0))
                .as("The first product category should be " + productThree.getCategory())
                .isEqualTo(productThree);

        Assertions.assertThat(productList.get(2))
                .as("The last product category should be " + productTwo.getCategory())
                .isEqualTo(productTwo);
    }
}