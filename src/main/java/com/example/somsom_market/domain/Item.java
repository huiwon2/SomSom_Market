package com.example.somsom_market.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Data
public class Item {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private int price;
    private String description;
    private List<String> imageUrl;
    private int wishCount;
}
