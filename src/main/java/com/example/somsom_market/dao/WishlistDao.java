package com.example.somsom_market.dao;

import com.example.somsom_market.domain.Wishlist;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class WishlistDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Wishlist insertWishlist(String account_id, Long item_id) {
        Wishlist wishlist = new Wishlist();
        wishlist.setAccountId(account_id);
        wishlist.setItemId(item_id);
        em.persist(wishlist);
        return wishlist;
    }

    @Transactional
    public void deleteWishlist(Wishlist wishlist) {
        if (em.contains(wishlist)) {
            em.remove(wishlist);
        } else {
            em.remove(em.merge(wishlist));
        }
    }
}
