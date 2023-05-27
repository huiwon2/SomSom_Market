package com.example.somsom_market.service;

import com.example.somsom_market.controller.SomsomItem.ItemRegistRequest;
import com.example.somsom_market.controller.SomsomItem.ItemUpdateRequest;
import com.example.somsom_market.dao.SomsomItemDao;
import com.example.somsom_market.domain.SomsomItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Setter
@Service
public class SomsomItemService {
    private int nextitemId;
    private Map<String, SomsomItem> somsomItem = new HashMap<String, SomsomItem>();
    @Autowired
    SomsomItemDao somsomItemDao;
    public SomsomItemService() {
    }
    public List<SomsomItem> getItemInfo() {
        return new ArrayList<SomsomItem>(somsomItem.values());
    }

    public SomsomItem getSomsomItemInfo(String memberId) {
        return somsomItem.get(memberId);
    }

    public SomsomItem registerSomsomItem(ItemRegistRequest regReq) {
        SomsomItem somsomItem = new SomsomItem();
        somsomItem.setTitle(regReq.getTitle());
        somsomItem.setPrice(regReq.getPrice());
        somsomItem.setDescription(regReq.getDescription());
        somsomItem.setImageUrl(Collections.singletonList(regReq.getImageUrl()));

    }
    public static String registerNewItem(ItemRegistRequest itemRegistRequest){
        return "";
    }
    public static void updateItem(ItemUpdateRequest itemUpdateRequest){

    }

}
