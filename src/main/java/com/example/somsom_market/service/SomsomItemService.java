package com.example.somsom_market.service;

import com.example.somsom_market.controller.ItemRegistRequest;
import com.example.somsom_market.domain.SomsomItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SomsomItemService {
    private int nextitemId;
    private Map<String, SomsomItem> somsomItem = new HashMap<String, SomsomItem>();

    public SomsomItemService() {
    }
    public List<SomsomItem> getMembers() {
        return new ArrayList<SomsomItem>(somsomItem.values());
    }

    public SomsomItem getSomsomItem(String memberId) {
        return somsomItem.get(memberId);
    }

//    public SomsomItem registerNewSomsomItem(ItemRegistRequest regReq) {
//        SomsomItem si = new SomsomItem(
//                "somsomItem" +  nextitemId,// ID 자동
//                                regReq.getTitle(),
//                                regReq.getPrice();
//
//        );
//        nextitemId++;
//        somsomItem.put(si.getItemId(), si);
//        return si;
//    }
}
