package com.example.somsom_market.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name="CART")
public class Cart {
    @Generated
    @Id
    private long cart_id;
    @NotNull
    private long user_id;
    @NotNull
    private long item_id;
    @NotNull
    private int quantity;

}
