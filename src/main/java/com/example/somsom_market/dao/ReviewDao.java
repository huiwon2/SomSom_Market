package com.example.somsom_market.dao;

import com.example.somsom_market.domain.Review;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    //나의 리뷰 리스트
    public List<Review> myReviewList(String userId){
       Query query = em.createQuery("SELECT r FROM Review r WHERE r.userId = :userId", Review.class);
       query.setParameter("userId", userId);
       List<Review> reviews = query.getResultList();
       return reviews;
    }
    //해당 상품에 대한 리뷰 리스트
   public List<Review> findReviewListByItemId(Long itemId) {
       Query query = em.createQuery("SELECT r FROM Review r WHERE r.orderItemId = :itemId", Review.class);
       query.setParameter("itemId", itemId);
       List<Review> reviews = query.getResultList();
       return reviews;
   }
}
