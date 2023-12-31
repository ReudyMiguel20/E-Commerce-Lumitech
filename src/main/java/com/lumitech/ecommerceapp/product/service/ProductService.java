package com.lumitech.ecommerceapp.product.service;

import com.lumitech.ecommerceapp.cart.model.dto.ProductCart;
import com.lumitech.ecommerceapp.product.model.dto.ProductDTO;
import com.lumitech.ecommerceapp.product.model.entity.Product;

import java.util.List;


public interface ProductService {
    Product createNewProduct(ProductDTO productDTO);

    void saveProduct(Product product);

    Product saveAndReturnProduct(Product product);

    Product convertToProduct(ProductDTO productDTO);

    ProductCart convertProductToProductCart(Product product, int quantity);

    Product findProductById(Long id);

    boolean doesProductExist(Product product);

    boolean isProductNull(Product product);

    void deleteAll();

    List<Product> getAllProducts();

    List<Product> getProducts(String sort, String order, int page);

    Product findByNameIgnoreCase(String productName);

    Product processUpdateProduct(Long id, ProductDTO newProductInfo);

    Product updateProductInfo(Product oldProduct, Product newProduct);

    void verifyUpdatedProductExistence(Product updatedProduct);

    void deleteProduct(Product productToBeDeleted);

    void deleteProductById(Long idProductToBeDeleted);

    void reduceProductStock(Product productToAdd, int quantity);

    void restoreProductStock(Product productToAdd, int quantity);
}
