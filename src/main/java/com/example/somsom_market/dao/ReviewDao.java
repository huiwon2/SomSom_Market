package com.example.somsom_market.dao;

import com.example.somsom_market.domain.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Repository
public class ReviewDao {
    @PersistenceContext
    private EntityManager em;

    //리뷰 추가
    @Transactional
    public int addNewReview(Review review){
        em.persist(review);
        return review.getReviewId();
    }

    //리뷰 수정
    @Transactional
    public int updateReview(Review review){
        em.merge(review);
        return review.getReviewId();
    }

    //리뷰 삭제
    @Transactional
    public void deleteReview(int reviewId){
        Review review = em.find(Review.class, reviewId);
        if(review == null){
            throw new ReviewNotFoundException(); //create exceptio
        }
        em.remove(em.merge(review));
    }
}
