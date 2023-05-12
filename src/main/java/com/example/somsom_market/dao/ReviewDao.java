package com.example.somsom_market.dao;

import com.example.somsom_market.domain.Review;

import java.util.List;

public interface ReviewDao {
    //사용자 PK로 리뷰 리스트 검색
    List<Review> getReviewList(int userId);

    //리뷰 추가
    int addNewReview(Review review);

    //리뷰 수정
    int updateReview(Review review);

    //리뷰 삭제
    void deleteReview(int reviewId);
}
