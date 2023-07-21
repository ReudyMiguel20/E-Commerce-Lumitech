package com.lumitech.ecommerceapp.product.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {

    @NotEmpty(message = "Name cannot be empty")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Min(value = 1, message = "Price should be greater than 0")
    private double price;

    @NotBlank(message = "Category cannot be blank")
    @NotEmpty(message = "Category cannot be empty")
    private String category;

    @NotBlank(message = "Brand cannot be blank")
    @NotEmpty(message = "Brand cannot be empty")
    private String brand;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Double.compare(that.price, price) == 0 && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(category, that.category) && Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, category, brand);
    }
}
