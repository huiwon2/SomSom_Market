package com.example.somsom_market.domain.CartTest;

import com.example.somsom_market.domain.item.SomsomItem;
import org.springframework.beans.support.PagedListHolder;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("serial")
public class CartItemTest implements Serializable {
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
