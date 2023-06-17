package com.example.somsom_market.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter
@RequiredArgsConstructor
@Entity
public class Review implements Serializable {

    @Column(name = "account_id")
    private String userId;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Id @Column(name = "review_id")
    @GeneratedValue
    private Long reviewId;

    private String description;
    private double score;
}
