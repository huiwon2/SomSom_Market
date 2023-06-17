package com.example.somsom_market.repository;

import com.example.somsom_market.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    CartItem findByItemId(Long itemId);
}
