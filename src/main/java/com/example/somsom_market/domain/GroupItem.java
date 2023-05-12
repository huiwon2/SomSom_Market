package com.example.somsom_market.domain;

import lombok.Data;

import java.util.Date;

@Data
public class GroupItem extends Item{
    private int sellerId;
    private int salesTarget;
    private int salesNow;
    private Date startDate;
    private Date endDate;
    private int status;
}
