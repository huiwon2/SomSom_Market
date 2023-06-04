package com.example.somsom_market.service;

import com.example.somsom_market.controller.PersonalItem.PersonalItemRequest;
import com.example.somsom_market.dao.PersonalItemDao;
import com.example.somsom_market.domain.ItemStatus;
import com.example.somsom_market.domain.PersonalItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalItemService {
    // 실제 메소드 구현은 dao 개발 후에 진행

    // Dao 선언
    @Autowired
    private PersonalItemDao personalItemDao;

    // 개인 게시글 리스트
    public List<PersonalItem> personalItemList() {
        return null;
    }

    // itemId로 검색
    public PersonalItem searchItem(int itemId) {
        return null;
    }

    // 새로운 personalItem 생성
    public PersonalItem registerNewItem(PersonalItemRequest itemRegistReq, String userId) {
        PersonalItem item = new PersonalItem();
        item.setTitle(itemRegistReq.getTitle());
        item.setPrice(itemRegistReq.getPrice());
        item.setDescription(itemRegistReq.getDescription());
        item.setStatus(ItemStatus.valueOf(itemRegistReq.getStatus()));
        item.setSellerId(userId);
//        item.setCategory_id(2);

        PersonalItem personalItem = personalItemDao.insertItem(item);

        return personalItem;
    }

    // 아이템 게시글 수정
    public PersonalItem updateItem(PersonalItemRequest itemRegistReq) {
        return null;
    }

    // 아이템 게시글 삭제
    public void deleteItem(int itemId) {

    }
}
