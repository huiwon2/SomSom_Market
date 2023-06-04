package com.example.somsom_market.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
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

    public static CartItem createCartItem(Cart cart, Optional<SomsomItem> item, int amount){
        CartItem cartItem = new CartItem();
        cartItem.setCart_id(cart);
        cartItem.setItem(item.get());
        cartItem.setCount(amount);
        return cartItem;
    }
//    개수 증가
    public void addCount(int count){
        this.count += count;
    }



}
