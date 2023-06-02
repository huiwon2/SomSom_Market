package com.example.somsom_market.repository;

import com.example.somsom_market.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findReviewByUserId(int userId); // userId로 리뷰 리스트 반환
}
