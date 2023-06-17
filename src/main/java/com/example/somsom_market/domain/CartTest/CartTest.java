package com.example.somsom_market.domain.CartTest;

import com.example.somsom_market.domain.item.SomsomItem;
import org.springframework.beans.support.PagedListHolder;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("serial")
public class CartTest implements Serializable {
    /* Private Fields */

    private final Map<Long, CartItemTest> itemMap = Collections.synchronizedMap(new HashMap<Long, CartItemTest>());

    private final PagedListHolder<CartItemTest> itemList = new PagedListHolder<CartItemTest>();

    /* JavaBeans Properties */

    public CartTest() {
        this.itemList.setPageSize(4);
    }

    public Iterator<CartItemTest> getAllCartItems() { return itemList.getSource().iterator(); }
    public PagedListHolder<CartItemTest> getCartItemList() { return itemList; }
    public int getNumberOfItems() { return itemList.getSource().size(); }

    /* Public Methods */

    public boolean containsItemId(Long itemId) {
        return itemMap.containsKey(itemId);
    }

    public void addItem(SomsomItem item, boolean isInStock) {
        CartItemTest cartItem = itemMap.get(item.getId());
        if (cartItem == null) {
            cartItem = new CartItemTest();
            cartItem.setItem(item);
            cartItem.setQuantity(0);
            cartItem.setInStock(isInStock);
            itemMap.put(item.getId(), cartItem);
            itemList.getSource().add(cartItem);
        }
        cartItem.incrementQuantity();
    }


    public SomsomItem removeItemById(Long itemId) {
        CartItemTest cartItem = itemMap.remove(itemId);
        if (cartItem == null) {
            return null;
        }
        else {
            itemList.getSource().remove(cartItem);
            return cartItem.getItem();
        }
    }

    public void incrementQuantityByItemId(Long itemId) {
        CartItemTest cartItem = itemMap.get(itemId);
        cartItem.incrementQuantity();
    }

    public void setQuantityByItemId(Long itemId, int quantity) {
        CartItemTest cartItem = itemMap.get(itemId);
        cartItem.setQuantity(quantity);
    }

    public double getSubTotal() {
        double subTotal = 0;
        Iterator<CartItemTest> items = getAllCartItems();
        while (items.hasNext()) {
            CartItemTest cartItem = (CartItemTest) items.next();
            SomsomItem item = cartItem.getItem();
            double listPrice = item.getPrice();
            int quantity = cartItem.getQuantity();
            subTotal += listPrice * quantity;
        }
        return subTotal;
    }

}
