package com.lumitech.ecommerceapp.authorities.repository;

import com.lumitech.ecommerceapp.authorities.model.entity.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
}
