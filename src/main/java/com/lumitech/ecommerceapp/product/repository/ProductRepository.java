package com.lumitech.ecommerceapp.product.repository;

import com.lumitech.ecommerceapp.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
    PagingAndSortingRepository<Product, Long> {

    Optional<Product> findByName(String name);
}
