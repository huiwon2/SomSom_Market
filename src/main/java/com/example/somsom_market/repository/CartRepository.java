package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findById(Long cart_id);
}
