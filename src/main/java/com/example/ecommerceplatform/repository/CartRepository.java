package com.example.ecommerceplatform.repository;

import com.example.ecommerceplatform.model.Cart;
import com.example.ecommerceplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
