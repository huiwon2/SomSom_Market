package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Cart;
import com.example.somsom_market.domain.CartItem;
import com.example.somsom_market.domain.GroupItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(long cart_id, long item_id);
}
