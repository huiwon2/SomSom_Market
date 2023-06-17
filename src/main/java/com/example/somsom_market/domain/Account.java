package com.example.somsom_market.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="ACCOUNT")
@SuppressWarnings("serial")
public class Account implements Serializable {
    @Id @Column(name = "account_id")
    private String id;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String nickName;
    @NotNull
    private String email;
    private String address;
    @Column(length = 5)
    private String zipcode;
    private String bankName;
    private String bankAccount;
    @NotNull
    private String phone;
//    수정 필요
    // private List<Integer> wishItem;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Order> orders = new ArrayList<>();

//    맞는지 잘 모름..
    public Cart getCart() {
        Cart cart = new Cart();
        Cart.createCart(cart.getAccount());
        return cart;
    }
}
