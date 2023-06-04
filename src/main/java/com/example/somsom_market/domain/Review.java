package com.example.somsom_market.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter
@RequiredArgsConstructor
@Entity
public class Review implements Serializable {

    private String userId;

    @Column(name="order_Item_id")
    private long orderItemId;

    @Id
    @GeneratedValue
    private long reviewId;

    private String description;
    private double score;
}
