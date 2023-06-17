package com.example.somsom_market.controller.Order;

import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.OrderItem;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequest {
    private Account account;
    private LocalDate orderDate;
    private String name;
    private String address;
    private String zipcode;
    private List<OrderItem> orderItems = new ArrayList<>();
}
