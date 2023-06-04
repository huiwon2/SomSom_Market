package com.example.somsom_market.controller.GroupItem;

import com.example.somsom_market.domain.ItemStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class GroupItemRequest {
    private long itemId;
    private String title;
    private int price;
    private String description;
    private List<String> imageUrl;
    private int wishCount;

    private String sellerId;
    private int salesTarget;
    private int salesNow;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date startDate;
    @DateTimeFormat(pattern="yyyy/MM/dd")
    private Date endDate;

    private String status;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getWishCount() {
        return wishCount;
    }

    public void setWishCount(int wishCount) {
        this.wishCount = wishCount;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public int getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(int salesTarget) {
        this.salesTarget = salesTarget;
    }

    public int getSalesNow() {
        return salesNow;
    }

    public void setSalesNow(int salesNow) {
        this.salesNow = salesNow;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
