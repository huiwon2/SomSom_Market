package com.example.somsom_market.service;

import com.example.somsom_market.controller.ItemRegistRequest;
import com.example.somsom_market.controller.ItemUpdateRequest;
import com.example.somsom_market.domain.PersonalItem;

import java.util.List;

public class PersonalItemService {
    // 실제 메소드 구현은 dao 개발 후에 진행

    // Dao 선언

    // 개인 게시글 리스트
    public List<PersonalItem> personalItemList() {
        return null;
    }

    // itemId로 검색
    public PersonalItem searchItem(int itemId) {
        return null;
    }

    // 새로운 personalItem 생성
    public PersonalItem registerNewItem(ItemRegistRequest itemRegistReq) {
        return null;
    }

    // 아이템 게시글 수정
    public PersonalItem updateItem(ItemRegistRequest itemRegistReq) {
        return null;
    }

    // 아이템 게시글 삭제
    public void deleteItem(int itemId) {

    }
}