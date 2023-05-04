package com.example.somsom_market.domain;

import lombok.Data;

@Data
public class PersonalItem extends Item{
    private String sellerId;
    private int status;
}
