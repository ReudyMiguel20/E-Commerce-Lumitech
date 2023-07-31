package com.lumitech.ecommerceapp.cart.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductFromCartDTO {

    @NotBlank
    @NotEmpty
    @JsonProperty(value = "productname")
    private String productName;

}
