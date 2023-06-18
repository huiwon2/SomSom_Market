package com.example.somsom_market.repository;


import com.example.somsom_market.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    void deleteByAccountId(String id);
    List<Order> findOrdersByAccountId(String id);
}
