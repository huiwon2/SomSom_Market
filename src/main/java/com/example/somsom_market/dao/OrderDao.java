package com.example.somsom_market.dao;

import com.example.somsom_market.domain.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public List<Order> getOrdersByUserId(String userId) throws DataAccessException {
        TypedQuery<Order> query = em.createQuery(
                "select order from Order order "
                        + "where order.userId=?1", Order.class);
        query.setParameter(1, userId);
        return query.getResultList();
    }

    public Order getOrder(int orderId) throws DataAccessException {
        return em.find(Order.class, orderId);
    }

    public void insertOrder(Order order) throws DataAccessException {

    }

    public void removeOrder(Order order) throws DataAccessException {

    }
}
