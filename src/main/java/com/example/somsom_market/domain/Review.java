package com.example.somsom_market.domain;

import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(appliesTo = "REVIEW")
public class Review {

    int userId;
    @Id
    @GeneratedValue
    int reviewId;
    String orderItemId;
    String description;
    float score;
}
