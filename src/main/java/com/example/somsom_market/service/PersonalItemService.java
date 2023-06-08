package com.example.somsom_market.service;

import com.example.somsom_market.controller.PersonalItem.PersonalItemRequest;
import com.example.somsom_market.dao.PersonalItemDao;
import com.example.somsom_market.domain.ItemStatus;
import com.example.somsom_market.domain.PersonalItem;
import com.example.somsom_market.repository.PersonalItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalItemService {
    // 실제 메소드 구현은 dao 개발 후에 진행

    // Dao 선언
    @Autowired
    private PersonalItemDao personalItemDao;
    @Autowired
    private PersonalItemRepository personalItemRepository;

    // 개인 게시글 리스트
    public List<PersonalItem> personalItemList() {
        List<PersonalItem> personalItemList = personalItemDao.findAll();
        return personalItemList;
    }

    // itemId로 검색
    public PersonalItem searchItem(long itemId) {
        Optional<PersonalItem> personalItem = personalItemRepository.findById(itemId);
        if (personalItem.isPresent()) return personalItem.get();
        return null;
    }

    // 새로운 personalItem 생성
    public PersonalItem registerNewItem(PersonalItemRequest itemRegistReq, String userId) {
        PersonalItem item = new PersonalItem();
        item.setTitle(itemRegistReq.getTitle());
        item.setPrice(itemRegistReq.getPrice());
        item.setDescription(itemRegistReq.getDescription());
        if (itemRegistReq.getStatus().equals("거래가능")) {
            item.setStatus(ItemStatus.INSTOCK);
        } else if (itemRegistReq.getStatus().equals("거래중")) {
            item.setStatus(ItemStatus.ING);
        } else {
            item.setStatus(ItemStatus.SOLDOUT);
        }
        item.setSellerId(userId);

        return personalItemDao.insertItem(item);
    }

    // 아이템 게시글 수정
    public PersonalItem updateItem(PersonalItemRequest itemRegistReq) {
        return personalItemDao.updateItem(itemRegistReq);
    }

    // 아이템 게시글 삭제
    public void deleteItem(Long itemId) {
        PersonalItem personalItem = searchItem(itemId);
        personalItemDao.deleteItem(personalItem);
    }
}
