package com.lumitech.ecommerceapp.product.model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;


@Builder
@NotNull
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewProduct {

    @NotEmpty(message = "Name cannot be empty")
    @NotBlank(message = "Name cannot be blank")
    private String name;

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
        NewProduct that = (NewProduct) o;
        return Double.compare(that.price, price) == 0 && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(category, that.category) && Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, category, brand);
    }

}
