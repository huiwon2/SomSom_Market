package com.example.somsom_market.dao;

import com.example.somsom_market.domain.Order;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface OrderDao {
    List<Order> getOrdersById(String id) throws DataAccessException;

    Order getOrder(int orderId) throws DataAccessException;

    void insertOrder(Order order) throws DataAccessException;

    public Order removeOrder(int orderId);
}
