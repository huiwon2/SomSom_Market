package com.example.somsom_market.controller.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter @Setter @ToString
@NoArgsConstructor
@SuppressWarnings("serial")
public class UserRegistRequest implements Serializable {
    private String userName;
    private String nickName;
    private String id;
    private String password;
    private String passwordCheck;
    private String email;
    private String address;
    private String zipcode;
    private String bankName;
    private String bankAccount;
    private String phoneNumber;

    public UserRegistRequest(String userName, String nickName, String id,
                             String email, String address, String zipcode, String bankName,
                             String bankAccount, String phoneNumber) {
        this.userName = userName;
        this.nickName = nickName;
        this.id = id;
        this.email = email;
        this.address = address;
        this.zipcode = zipcode;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.phoneNumber = phoneNumber;
    }

    public UserRegistRequest(String userName, String nickName, String id,
                             String email, String phoneNumber) {
        this.userName = userName;
        this.nickName = nickName;
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
