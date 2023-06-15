package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findById(String user_id);
}
