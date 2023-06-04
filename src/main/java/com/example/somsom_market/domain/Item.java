package com.example.somsom_market.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Data
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private long id;

    private String title;
    private String description;
    private int price;
    private int wishCount;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ElementCollection
    private List<String> imageUrl;
}
