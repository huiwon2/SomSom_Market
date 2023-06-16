package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findById(String account_id);

    Cart findByAccount(String account_id);
}
