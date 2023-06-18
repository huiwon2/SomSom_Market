package com.example.somsom_market.repository;

import com.example.somsom_market.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

    private String accountId; //회원 아이디
    private OrderStatus orderStatus; //주문 상태[PROCESSED, CANCEL]
}
