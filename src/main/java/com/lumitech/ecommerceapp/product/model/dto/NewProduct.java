package com.lumitech.ecommerceapp.product.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class NewProduct {

    @NotEmpty(message = "Name cannot be empty")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Min(value = 0, message = "Price cannot be less than 0")
    private double price;

    @NotBlank(message = "Category cannot be blank")
    @NotEmpty(message = "Category cannot be empty")
    private String category;

    @NotBlank(message = "Brand cannot be blank")
    @NotEmpty(message = "Brand cannot be empty")
    private String brand;

    public NewProduct() {
    }

    public NewProduct(String name, String description, double price, String category, String brand) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
