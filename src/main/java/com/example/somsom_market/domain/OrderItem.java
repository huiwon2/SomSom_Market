package com.example.somsom_market.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class OrderItem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int quantity;

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setQuantity(quantity);
        // TODO: 2023/05/27 개인거래의 경우 item status 품절로 변경하는 로직
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(quantity);
    }

    //==조회 로직==//

    //==주문상품 전체 가격 조회==//
    public int getTotalPrice() {
        return getOrderPrice() * getQuantity();
    }
}
