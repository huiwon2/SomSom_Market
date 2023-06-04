package com.example.somsom_market.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter
@RequiredArgsConstructor
@Entity
public class Review implements Serializable {

    private String userId;

    private Long orderItemId;

    @Id
    @GeneratedValue
    private Long reviewId;

    private String description;
    private double score;
}
