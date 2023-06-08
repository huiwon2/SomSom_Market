package com.example.somsom_market.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@SequenceGenerator(name="SEQ_ITEM", sequenceName="ITEM_ID_SEQ", allocationSize=1)
@Getter
@Setter
public abstract class Item {
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ITEM")
    @Column(name = "item_id")
    private Long id;

    private String title;
    private String description;
    private int price;
    private int wishCount;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;
}
