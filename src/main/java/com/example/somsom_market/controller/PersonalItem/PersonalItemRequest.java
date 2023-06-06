package com.example.somsom_market.controller.PersonalItem;

import lombok.Data;

import java.util.List;

@Data
public class PersonalItemRequest {
    private long itemId;
    private String sellerId;

    private String title;
    private int price;
    private String description;
    // private List<String> imageUrl;
    private String status; // 거래가능 / 거래중 / 거래완료
}
