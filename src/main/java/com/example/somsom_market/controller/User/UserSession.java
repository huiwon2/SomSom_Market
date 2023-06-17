package com.example.somsom_market.controller.User;

import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.item.SomsomItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.support.PagedListHolder;

import java.io.Serializable;

@Getter @Setter
@SuppressWarnings("serial")
public class UserSession implements Serializable {
    private Account account;

    private PagedListHolder<SomsomItem> mySomsomList;

    public UserSession(Account account) {
        this.account = account;
    }

    public PagedListHolder<SomsomItem> getMySomsomList() {
        return mySomsomList;
    }
}
