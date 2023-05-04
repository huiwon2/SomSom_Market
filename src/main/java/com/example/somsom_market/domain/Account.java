package com.example.somsom_market.domain;

import lombok.Data;

import java.util.List;

@Data
public class Account {
    private int userId;
    private String userName;
    private String nickName;
    private String id;
    private String password;
    private String email;
    private String address;
    private String bankName;
    private String bankAccount;
    private String phone;
    private List<Integer> wishItem;

}
