package com.example.somsom_market.controller.Review;

public class ReviewRequest {
    private int userId;
    private int orderItemId;
    private String description;
    private float score;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription() {
        this.description = description;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
