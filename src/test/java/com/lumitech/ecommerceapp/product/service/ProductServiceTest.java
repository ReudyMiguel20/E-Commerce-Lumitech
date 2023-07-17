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

}
