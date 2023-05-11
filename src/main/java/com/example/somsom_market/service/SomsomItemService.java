package com.example.somsom_market.service;

import com.example.somsom_market.controller.ItemRegistRequest;
import com.example.somsom_market.controller.ItemUpdateRequest;
import com.example.somsom_market.domain.SomsomItem;
import com.example.somsom_market.model.ItemInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Service
public class SomsomItemService {
    private int nextitemId;
    private Map<String, SomsomItem> somsomItem = new HashMap<String, SomsomItem>();

    public SomsomItemService() {
    }
    public List<SomsomItem> getItemInfo() {
        return new ArrayList<SomsomItem>(somsomItem.values());
    }

    public SomsomItem getSomsomItemInfo(String memberId) {
        return somsomItem.get(memberId);
    }

    public SomsomItem registerNewSomsomItem(ItemRegistRequest regReq) {
        SomsomItem si = new SomsomItem(
                "somsomItem" +  nextitemId,// ID 자동
                                regReq.getTitle(),
                                regReq.getPrice(),
                                regReq.getDescription(),
                                regReq.getImageUrl());
        nextitemId++;
//        somsomItem.put(si.getItemId(), si);
        return si;
    }
    public static String registerNewItem(ItemRegistRequest itemRegistRequest){
        return "";
    }
    public static void updateItem(ItemUpdateRequest itemUpdateRequest){

    }

}
