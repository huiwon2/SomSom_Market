package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByAccountIdAndItemId(String id, Long itemId);
    List<Wishlist> findByAccountId(String id);
}
