package com.example.somsom_market.domain.CartSession;

import com.example.somsom_market.domain.item.SomsomItem;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@SuppressWarnings("serial")
@Getter @Setter
public class CartItemSession implements Serializable {

    private SomsomItem item;
    private int quantity;
    private boolean inStock;

    public void incrementQuantity() {
        quantity++;
    }
}
