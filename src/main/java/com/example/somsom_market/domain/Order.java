package com.example.somsom_market.domain;

import com.example.somsom_market.domain.CartSession.CartItemSession;
import com.example.somsom_market.domain.CartSession.CartSession;
import com.example.somsom_market.domain.item.SomsomItem;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.FetchType.LAZY;


@Entity
@Table(name = "orders")
@Getter @Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본생성자 사용 불가
@SequenceGenerator(name="SEQ_ORDER", sequenceName="ORDER_ID_SEQ", allocationSize=1)
public class Order implements Serializable{

    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ORDER")
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    private LocalDate orderDate;
    private String name;
    private String phone;

    @Column(name = "ship_address")
    private String address;

    @Column(length = 5)
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private ShipState shipState;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //==연관관계 메서드==//
    public void setAccount(Account account) {
        this.account = account;
        account.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==생성 메서드==//
    public static Order initOrder(Account account, OrderItem... orderItems) {
        Order order = new Order();
        order.setAccount(account);
        order.setOrderDate(LocalDate.now());
        order.setName(account.getName());
        order.setPhone(account.getPhone());
        order.setAddress(account.getAddress());
        order.setZipcode(account.getZipcode());
        order.setShipState(ShipState.PROCESSING);
        order.setStatus(OrderStatus.PROCESSED);
        order.setTotalPrice(order.getTotalPrice());
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }
//
//    // TODO: 2023/06/04 카트 아이템 바탕으로 주문 생성하는 로직 필요
//    public void initOrder(Account account, OrderItem... orderItems) {
//        this.account = account;
//        orderDate = LocalDate.now();
//        name = account.getName();
//        phone = account.getPhone();
//        address = account.getAddress();
//        zipcode = account.getZipcode();
//        shipState = ShipState.PROCESSING;
//        status = OrderStatus.PROCESSED;
//
//        totalPrice = cart.getSubTotal();
//
//        for (OrderItem orderItem : orderItems) {
//            order.addOrderItem(orderItem);
//        }
//    }

    public static CartItem createCartItem(Cart cart, SomsomItem item, int amount){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(amount);
        return cartItem;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (shipState == ShipState.DELIVERED) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
