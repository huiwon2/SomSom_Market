package com.example.somsom_market.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
@Data
@Table(name = "orders")
public class Order implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private Long userId;

    @Temporal(TemporalType.DATE)
    private String orderDate;

    private String address;

    private String zipcode;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int totalPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
}
