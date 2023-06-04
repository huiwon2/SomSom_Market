package com.example.somsom_market.repository;


import com.example.somsom_market.domain.GroupItem;
import com.example.somsom_market.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
