package com.lumitech.ecommerceapp.cart.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCart {

    @NotEmpty
    @NotBlank
    private String name;

    @NotEmpty
    private double price;

    @NotEmpty
    @NotBlank
    private String category;

    @NotEmpty
    @NotBlank
    private String brand;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;
}
