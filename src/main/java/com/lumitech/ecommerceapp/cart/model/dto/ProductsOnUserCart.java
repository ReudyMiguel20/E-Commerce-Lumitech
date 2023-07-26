package com.lumitech.ecommerceapp.cart.model.dto;

import com.lumitech.ecommerceapp.product.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsOnUserCart {

    List<Product> productList = new ArrayList<>();

    private double total;
}
