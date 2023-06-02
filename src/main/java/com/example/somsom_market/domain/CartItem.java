package com.example.somsom_market.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue
    private long cartItem_id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cart_id")
    private Cart cart_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_id")
    private Item item;

//    개수
    private int count;

    public static CartItem createCartItem(Cart cart, Item item, int amount){
        CartItem cartItem = new CartItem();
        cartItem.setCart_id(cart);
        cartItem.setItem(item);
        cartItem.setCount(amount);
        return cartItem;
    }



}
