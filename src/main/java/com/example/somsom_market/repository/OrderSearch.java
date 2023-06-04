package com.example.somsom_market.repository;

import com.example.somsom_market.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

    private String memberName; //회원 이름
    private OrderStatus orderStatus; //주문 상태[PROCESSING, INDELIVERY, DELIVERED, CANCEL]
}
