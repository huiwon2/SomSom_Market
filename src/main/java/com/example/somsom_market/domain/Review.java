package com.example.somsom_market.domain;

import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(appliesTo = "REVIEW")
public class Review implements Serializable {

    private int userId;

    @Column(name="order_Item_id")
    private long orderItemId;

    @Id
    @GeneratedValue
    private long reviewId;

    private String description;
    private double score;
}
