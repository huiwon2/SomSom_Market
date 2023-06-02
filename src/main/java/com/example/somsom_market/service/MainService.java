package com.example.somsom_market.service;

import com.example.somsom_market.domain.Item;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {
    private Map<String, Item> itemMap = new HashMap<String, Item>();

    // 전체 상품 조회
    public List<Item> getAllItems() {
        return null;
    }

    // 추천 상품 조회
    public List<Item> getMainItems() {
        return null;
    }

    // 아이템 상세 정보 조회
    public Item getItemDetail(long itemId) {
        return null;
    }

    // 상품 title 검색
//    public List<Item> searchItem(String query) {
//        return null;
//    }

    // type에 해당하는 상품 title 검색, 카테고리 조회
    public List<Item> searchItemByQuery(String query, String type) {

        return null;
    }
}
