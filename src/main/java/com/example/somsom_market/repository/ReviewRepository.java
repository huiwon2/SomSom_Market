package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewByUserId(String userId); // userId로 리뷰 리스트 반환
    void deleteByUserId(String id);
}
