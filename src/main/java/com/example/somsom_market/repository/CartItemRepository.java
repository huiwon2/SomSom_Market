package com.example.somsom_market.repository;

import com.example.somsom_market.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository {
    CartItem findByCartIdAndItemId(long cart_id, long item_id);
}
