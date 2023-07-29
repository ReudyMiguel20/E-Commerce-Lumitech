package com.lumitech.ecommerceapp.cart.model.dto;

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
public class UserProductCart {

    List<ProductCart> productList = new ArrayList<>();

    private double total;
}
