package com.example.somsom_market.service;

import com.example.somsom_market.dao.OrderDao;
import com.example.somsom_market.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public Order getOrderInfo(int orderId) {
        return orderDao.getOrder(orderId);
    }

    public List<Order> getOrderByUserId(String id) {
        return orderDao.getOrdersByUserId(id);
    }

    public Order removeOrder(int orderId) {
        return orderDao.removeOrder(orderId);
    }
}
