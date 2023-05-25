package com.example.somsom_market.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(appliesTo = "ORDER")
public class Order {
    @Id
    @GeneratedValue
    private int orderId;
    @NotNull
    private String userName;
    private String orderDate;
    private String phoneNumber;
    private String shipAddress;
    private String shipState;
    private int totalPrice;
    private int status;
    private int itemType;
//    수정 필요..
    private Map<Integer, Integer> itemInfo;
}
