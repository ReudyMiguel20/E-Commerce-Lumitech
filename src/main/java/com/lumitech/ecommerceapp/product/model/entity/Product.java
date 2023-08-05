package com.lumitech.ecommerceapp.product.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

/* Ideas for the product entity
1- Can store images soon in the database */

@Entity
@Table(name = "products")
@Builder
@NotNull
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String name;

    private String description;

    private double price;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private String brand;

    private String image;

    private int stock;

    //This equal method doesn't compare the id of the object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(category, product.category) && Objects.equals(brand, product.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, category, brand);
    }
}
