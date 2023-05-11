package com.example.somsom_market.model;

import lombok.Data;

@Data
public class ItemInfo {
    String title;
    int price;
    String description;
    String imageUrl;
    public ItemInfo(){

    }
    public ItemInfo(String title, String description, int price, String imageUrl){
        this.title = title;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
