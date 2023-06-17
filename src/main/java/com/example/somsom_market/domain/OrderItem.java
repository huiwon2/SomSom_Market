package com.example.somsom_market.domain;

import com.example.somsom_market.domain.item.SomsomItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name="SEQ_ORDER_ITEM", sequenceName="ORDER_ITEM_ID_SEQ", allocationSize=1)
public class OrderItem {
    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ORDER_ITEM")
    @Column(name = "order_item_id")
    private Long id;

//    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private SomsomItem item;

    private int orderPrice;
    private int quantity;

    //==생성 메서드==//
    public static OrderItem createOrderItem(SomsomItem item, int orderPrice, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setQuantity(quantity);
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
