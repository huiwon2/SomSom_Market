package com.example.somsom_market.controller.User;

import com.example.somsom_market.domain.Account;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@SuppressWarnings("serial")
public class UserSession implements Serializable {
    private Account account;

    public UserSession(Account account) {
        this.account = account;
    }
}
