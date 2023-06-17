package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByAccountId(String account_id);

    Cart findByAccount(String account_id);

    void deleteByAccountId(String id);
}
