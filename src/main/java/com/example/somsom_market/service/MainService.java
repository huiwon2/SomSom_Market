package com.example.somsom_market.service;

import com.example.somsom_market.dao.PersonalItemDao;
import com.example.somsom_market.domain.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {

    @Autowired
    private PersonalItemDao personalItemDao;

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
        String typeStr;
        if (type.equals("통합검색")) {
            return personalItemDao.searchAllItem(query);
        } else if (type.equals("솜솜이")) {
            typeStr = "SOMSOM";
        } else if (type.equals("장터")) {
            typeStr = "PERSONAL";
        } else {
            typeStr = "GROUP";
        }
        return personalItemDao.searchItem(query, typeStr);
    }
}
