package com.example.somsom_market.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@Entity
@SequenceGenerator(name="SEQ_CART", sequenceName="CART_ID_SEQ", allocationSize=1)
public class Cart {
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CART")
    @Id
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    @NotNull
    private Account account;

    private long count;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();
    @NotNull
    private int total_quantity;

    public static Cart createCart(Account account){
        Cart cart = new Cart();
        cart.setCount(0);
        cart.setAccount(account);
        cart.setTotal_quantity(0);
        return cart;
    }

}
