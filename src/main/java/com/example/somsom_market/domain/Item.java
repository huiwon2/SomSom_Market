package com.example.somsom_market.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="ITEM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ITEM_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)
// 타입 구분 필요함(singledTable로 했을 때)
public class Item {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private int price;
    private String description;
//    private List<String> imageUrl;
    private int wishCount;
}
