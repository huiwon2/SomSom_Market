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
    public List<Review> findByUserId(String userId){
        return reviewDao.myReviewList(userId);
    }

    //orderItemId로 리뷰 리스트 반환
    public List<Review> findByItemId(long itemId){
        return reviewDao.findReviewListByItemId(itemId);
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
    public long registerNewReview(ReviewRequest req){
        return reviewDao.addNewReview(reqToReview(req));
    }

    //리뷰 수정
    public long updateReview(ReviewRequest req){
        return reviewDao.updateReview(reqToReview(req));
    }

    //리뷰 삭제
    public void deleteReview(long reviewId){
        reviewDao.deleteReview(reviewId);
    }
}
