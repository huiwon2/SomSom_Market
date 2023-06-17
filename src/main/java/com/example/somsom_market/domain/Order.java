package com.example.somsom_market.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Order implements Serializable{

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    @Column(name = "order_date")
    private LocalDate orderDate;
    private String name;
    private String phone;
    @Column(name = "ship_address")
    private String address;
    private String zipcode;

    @Column(name = "ship_state")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_price")
    private int totalPrice;

    @JsonIgnore
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
    public static Order createOrder(Account account, OrderItem orderItem) {
        Order order = new Order();
        order.setAccount(account);
        order.addOrderItem(orderItem);
        order.setStatus(OrderStatus.PROCESSING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    // TODO: 2023/06/04 카트 아이템 바탕으로 주문 생성하는 로직 필요
    public void createOrderFromCart(Account account, Cart cart) {
//        setAccount(account);
//        orderDate.setOrderDate(LocalDate.now());
//
//        setName(account.getUserName());
//        shipToLastName = account.getLastName();
//        shippingAddress = account.getAddress();
//
//        billToFirstName = account.getFirstName();
//        billToLastName = account.getLastName();
//        billingAddress = new Address(account.getAddress());
//
//        totalPrice = cart.getSubTotal();
//
//        creditCard = "999 9999 9999 9999";
//        expiryDate = "12/03";
//        cardType = "Visa";
//        courier = "UPS";
//        locale = "CA";
//        status = "P";
//        timestamp = orderDate;
//
//        this.lineItems = new ArrayList<LineItem>();
//        Iterator<CartItem> i = cart.getAllCartItems();
//        while (i.hasNext()) {
//            CartItem cartItem = (CartItem) i.next();
//            addLineItem(cartItem);
//        }
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (status == OrderStatus.DELIVERED) {
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
