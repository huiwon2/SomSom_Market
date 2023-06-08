package com.example.somsom_market.service;

import com.example.somsom_market.domain.GroupItem;
import com.example.somsom_market.domain.PersonalItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {


    // 사용자 PK로 개인 판매 위시리스트 검색
    public List<PersonalItem> getPersonalWishlist(String id) {
        return null;
    }

    // 사용자 PK로 공동구매 위시리스트 검색
    public List<GroupItem> getGroupWishlist(String id) {
        return null;
    }

    // 해당 사용자에 해당 아이템 위시리스트 추가
    public void addWishlist(String id, Long itemId) {

    }

    // 해당 사용자에 해당 아이템 위시리스트 삭제
    public void cancelWishlist(String id, Long itemId) {

    }
}
