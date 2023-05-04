package com.example.somsom_market.controller.Order;

import com.example.somsom_market.domain.Item;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String userName;
    private String phoneNumber;
    private String shipAddress;
    private List<Item> orderItem;
    private int totalPrice;
}
