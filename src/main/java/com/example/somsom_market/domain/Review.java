package com.example.somsom_market.domain;

import lombok.Data;
@Data
public class Review {
    int userId;
    int reviewId;
    String orderItemId;
    String description;
    float score;
}
