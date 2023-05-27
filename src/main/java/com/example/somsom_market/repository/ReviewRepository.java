package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findReviewByUserId(int userId);
}
