package com.example.somsom_market.dao;

import com.example.somsom_market.domain.Review;
import org.springframework.dao.DataAccessException;
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
    public long addNewReview(Review review){
        em.persist(review);
        return review.getReviewId();
    }

    //리뷰 수정
    @Transactional
    public long updateReview(Review review) throws IllegalArgumentException{
        em.merge(review);
        return review.getReviewId();
    }

    //리뷰 삭제
    @Transactional
    public void deleteReview(long reviewId){
        Review review = em.find(Review.class, reviewId);
        em.remove(em.merge(review));
    }
}
