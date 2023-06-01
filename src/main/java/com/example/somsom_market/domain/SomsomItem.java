package com.example.somsom_market.domain;

import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@DiscriminatorValue(value="somsom")
public class SomsomItem extends Item{

}
