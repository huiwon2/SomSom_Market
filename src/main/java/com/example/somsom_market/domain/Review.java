package com.example.somsom_market.domain;

import lombok.Data;

import java.util.List;
@Data
public class Review {
    int userId;
    int reviewId;
    String orderItemId;
    String description;
    float score;
}
