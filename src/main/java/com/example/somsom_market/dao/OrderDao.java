package com.example.somsom_market.dao;

import com.example.somsom_market.domain.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderDao {

    private final EntityManager em;

    public OrderDao(EntityManager em) {
        this.em = em;
    }

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllByUserId(long userId) throws DataAccessException {
        TypedQuery<Order> query = em.createQuery(
                "select order from Order order "
                        + "where order.getUserId()=?1", Order.class);
        query.setParameter(1, userId);
        return query.getResultList();
    }
}
