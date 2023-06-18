package com.example.somsom_market.domain.CartSession;

import com.example.somsom_market.domain.item.SomsomItem;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CartItemSession implements Serializable {
    /* Private Fields */

    private SomsomItem item;
    private int quantity;
    private boolean inStock;

    /* JavaBeans Properties */

    public boolean isInStock() { return inStock; }
    public void setInStock(boolean inStock) { this.inStock = inStock; }

    public SomsomItem getItem() { return item; }
    public void setItem(SomsomItem item) {
        this.item = item;
    }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        if (item != null) {
            return item.getPrice() * quantity;
        }
        else {
            return 0;
        }
    }

    /* Public methods */

    public void incrementQuantity() {
        quantity++;
    }
}
