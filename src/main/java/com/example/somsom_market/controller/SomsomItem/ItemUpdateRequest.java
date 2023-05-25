package com.example.somsom_market.controller.SomsomItem;

import lombok.Data;

@Data
public class ItemUpdateRequest {
    private String title;
    private int price;
    private String description;
    private String imageUrl;
}