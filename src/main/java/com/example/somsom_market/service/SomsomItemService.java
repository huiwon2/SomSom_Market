package com.example.somsom_market.service;

import com.example.somsom_market.controller.SomsomItem.ItemRegistRequest;
import com.example.somsom_market.controller.SomsomItem.ItemUpdateRequest;
import com.example.somsom_market.dao.SomsomItemDao;
import com.example.somsom_market.domain.SomsomItem;
import com.example.somsom_market.repository.SomsomItemRepository;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class SomsomItemService {
    @Autowired
    private SomsomItemRepository somsomItemRepository;
    @Autowired
    private SomsomItemDao somsomItemDao;

//   아이템 검색
    public SomsomItem getSomsomItem(String id){
        Optional<SomsomItem> somsomItem = somsomItemRepository.findById(id);
        if(somsomItem.isPresent()) {
            return somsomItem.get();
        }
        return null;
    }
//  게시글 등록
    public SomsomItem insertSomsomItem(ItemRegistRequest regReq) {
        SomsomItem somsomItem = new SomsomItem();
        somsomItem.setTitle(regReq.getTitle());
        somsomItem.setPrice(regReq.getPrice());
        somsomItem.setDescription(regReq.getDescription());
        somsomItem.setImageUrl(Collections.singletonList(regReq.getImageUrl()));

        somsomItemDao.insertSomsomItem(somsomItem);
        return somsomItem;
    }
//    게시글 수정
    public static void updateSomsomItem(ItemUpdateRequest itemUpdateRequest){

    }
//    게시글 삭제
    public static void deleteSomsomItem(){

    }

}
