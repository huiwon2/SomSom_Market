package com.example.somsom_market.repository;

import com.example.somsom_market.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cart_id, Long item_id);

    CartItem findByItemId(Long itemId);
}
