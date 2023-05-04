package com.example.somsom_market.domain;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class  Order {
    private int orderId;
    private String userName;
    private Date orderDate;
    private String phoneNumber;
    private String shipAddress;
    private String shipState;
    private int totalPrice;
    private int status;
    private int itemType;
    private Map<Integer, Integer> itemInfo;
}
