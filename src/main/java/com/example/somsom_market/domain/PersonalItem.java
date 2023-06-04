package com.example.somsom_market.domain;

import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@Entity
@DiscriminatorValue(value="PERSONAL")
public class PersonalItem extends Item{
    @Column(name="seller_id")
    private String sellerId;
    private int category_id;
    private String status;
}
