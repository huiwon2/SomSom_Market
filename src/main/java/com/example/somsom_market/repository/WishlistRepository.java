package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

}
