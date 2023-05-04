package com.example.somsom_market.domain;

import lombok.Data;

import java.util.List;

@Data
public class Item {
    private int itemId;
    private String title;
    private int price;
    private String description;
    private List<String> imageUrl;
    private int wishCount;
}
