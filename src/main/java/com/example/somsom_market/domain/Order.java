package com.example.somsom_market.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@SuppressWarnings("serial")
public class Order implements Serializable {
    private int orderId;
    private String userName;
    private String orderDate;
    private String phoneNumber;
    private String shipAddress;
    private String shipState;
    private int totalPrice;
    private int status;
    private int itemType;
    private Map<Integer, Integer> itemInfo;
}
