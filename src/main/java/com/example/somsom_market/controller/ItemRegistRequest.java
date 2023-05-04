package com.example.somsom_market.controller;

import lombok.Data;


@Data
public class ItemRegistRequest {
    private String title;
    private int price;
    private String description;
    private String imageUrl;
}
