package com.example.somsom_market.service;

import com.example.somsom_market.controller.Review.ReviewRequest;
import com.example.somsom_market.dao.ReviewDao;
import com.example.somsom_market.domain.Review;
import com.example.somsom_market.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewService {
    @Autowired
    ReviewDao reviewDao;

    @Autowired
    ReviewRepository reviewRepository;
    //userId로 리뷰 리스트 반환
    public List<Review> getReviewListByUserId(int userId){
        return reviewRepository.findReviewByUserId(userId);
    }

    private static Review reqToReview(ReviewRequest req){
        Review tmp = new Review();
        //reviewId = AutoGenerate
        tmp.setScore(req.getScore());
        tmp.setDescription(req.getDescription());
        tmp.setUserId(req.getUserId());
        tmp.setScore(req.getScore());
        return tmp;
    }

    //리뷰 추가
    public int registerNewReview(ReviewRequest req){
        return reviewDao.addNewReview(reqToReview(req));
    }

    //리뷰 수정
    public int updateReview(ReviewRequest req){
        return reviewDao.updateReview(reqToReview(req));
    }

    //리뷰 삭제
    public void deleteReview(int reviewId){
        reviewDao.deleteReview(reviewId);
    }
}
