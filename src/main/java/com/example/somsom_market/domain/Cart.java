package com.example.somsom_market.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @NotNull
    private Account account;
    private long count;
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();
    @NotNull
    private int total_quantity;

    public static Cart createCart(Account account){
        Cart cart = new Cart();
        cart.setCount(0);
        cart.setAccount(account);
        return cart;
    }

}
