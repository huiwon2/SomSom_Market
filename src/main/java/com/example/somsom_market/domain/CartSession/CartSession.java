package com.example.somsom_market.domain.CartSession;

import com.example.somsom_market.domain.item.SomsomItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.support.PagedListHolder;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("serial")
@Getter @Setter
public class CartSession implements Serializable {

    private final Map<Long, CartItemSession> itemMap = Collections.synchronizedMap(new HashMap<Long, CartItemSession>());
    private final PagedListHolder<CartItemSession> itemList = new PagedListHolder<CartItemSession>();

    public CartSession() {
        this.itemList.setPageSize(4);
    }
    public Iterator<CartItemSession> getAllCartItems() { return itemList.getSource().iterator(); }
    public PagedListHolder<CartItemSession> getCartItemList() { return itemList; }
    public int getNumberOfItems() { return itemList.getSource().size(); }

    public boolean containsItemId(Long itemId) {
        return itemMap.containsKey(itemId);
    }

    public void addItem(SomsomItem item, boolean isInStock) {
        CartItemSession cartItem = itemMap.get(item.getId());
        if (cartItem == null) {
            cartItem = new CartItemSession();
            cartItem.setItem(item);
            cartItem.setQuantity(0);
            cartItem.setInStock(isInStock);
            itemMap.put(item.getId(), cartItem);
            itemList.getSource().add(cartItem);
        }
        cartItem.incrementQuantity();
    }


    public SomsomItem removeItemById(Long itemId) {
        CartItemSession cartItem = itemMap.remove(itemId);
        if (cartItem == null) {
            return null;
        }
        else {
            itemList.getSource().remove(cartItem);
            return cartItem.getItem();
        }
    }

    public void incrementQuantityByItemId(Long itemId) {
        CartItemSession cartItem = itemMap.get(itemId);
        cartItem.incrementQuantity();
    }

    public void setQuantityByItemId(Long itemId, int quantity) {
        CartItemSession cartItem = itemMap.get(itemId);
        cartItem.setQuantity(quantity);
    }

    public int getSubTotal() {
        int subTotal = 0;
        Iterator<CartItemSession> items = getAllCartItems();
        while (items.hasNext()) {
            CartItemSession cartItem = (CartItemSession) items.next();
            SomsomItem item = cartItem.getItem();
            double listPrice = item.getPrice();
            int quantity = cartItem.getQuantity();
            subTotal += listPrice * quantity;
        }
        return subTotal;
    }

}
