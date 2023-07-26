package com.lumitech.ecommerceapp.cart.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToCartDTO {


    @NotBlank
    @NotEmpty
    @JsonProperty(value = "productname")
    private String productName;

    @Min(value = 1)
    @JsonProperty(value = "quantity")
    private int quantity;

}
