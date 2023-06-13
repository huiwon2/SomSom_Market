package com.example.somsom_market.domain;

import com.example.somsom_market.domain.item.Item;
import com.example.somsom_market.domain.item.SomsomItem;
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
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_id")
    private Item item;

//    개수
    private int count;

    public static CartItem createCartItem(Cart cart, Optional<SomsomItem> item, int amount){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item.get());
        cartItem.setCount(amount);
        return cartItem;
    }
//    개수 증가
    public void addCount(int count){
        this.count += count;
    }



}
