package com.lumitech.ecommerceapp.product.service;

import com.lumitech.ecommerceapp.product.model.dto.ProductDTO;
import com.lumitech.ecommerceapp.product.model.entity.Product;
import com.lumitech.ecommerceapp.product.repository.ProductRepository;
import com.lumitech.ecommerceapp.product.service.impl.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Saving new product should be saved and not null")
    void saveNewProduct_SuccessfullySavesProductAndIsNotNull() {
        //Arrange
        ProductDTO productDto = ProductDTO.builder()
                .name("Test")
                .description("Test")
                .price(1.0)
                .category("Test")
                .brand("Test")
                .build();
        Product newProduct = productService.convertToProduct(productDto);

        //Mock
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(newProduct);

        //Act
        Product savedProduct = productService.saveAndReturnProduct(newProduct);

        //Assert
        Assertions.assertThat(savedProduct)
                .as("Product is null")
                .isNotNull();

        Assertions.assertThat(savedProduct)
                .as("newProduct is not equal to savedProduct")
                .isEqualTo(newProduct);
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

        List<Product> productList = Arrays.asList(product1, product2);

        //Mock
        when(productRepository.findAll()).thenReturn(productList);

        //Act
        List<Product> result = productService.getAllProducts();

        //Assert
        Assertions.assertThat(result.size()).as("Does not contain the correct number of products").isGreaterThan(1);
        Assertions.assertThat(result).as("The list of products is null").isNotNull();
    }

    @Test
    void getAll_SortedByPriceAsc() {
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

        List<Product> productList = Arrays.asList(productTwo, productThree, productOne);

        when(productRepository.findAll(Sort.by("price").ascending())).thenReturn(productList);

        //Act
        List<Product> result = productRepository.findAll(Sort.by("price").ascending());

        //Assertions
        Assertions.assertThat(result)
                .as("The list is empty")
                .isNotEmpty();

        Assertions.assertThat(result.size())
                .as("List size is expected to be 3")
                .isEqualTo(3);

        Assertions.assertThat(result.get(0))
                .as("The product with the highest price is not first")
                .isEqualTo(productTwo);

        Assertions.assertThat(result.get(2))
                .as("The product with the lowest price is not last")
                .isEqualTo(productOne);
    }

}
