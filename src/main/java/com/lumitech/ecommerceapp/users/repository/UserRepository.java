package com.lumitech.ecommerceapp.users.repository;

import com.lumitech.ecommerceapp.users.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
