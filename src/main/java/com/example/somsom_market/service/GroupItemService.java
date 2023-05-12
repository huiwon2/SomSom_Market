package com.example.somsom_market.service;

import com.example.somsom_market.controller.GroupItem.GroupItemRequest;
import com.example.somsom_market.dao.AccountDao;
import com.example.somsom_market.dao.GroupItemDao;
import com.example.somsom_market.domain.GroupItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GroupItemService {
    /*Map<String, GroupItem> map = new HashMap<String, GroupItem>();

    public Map<String, GroupItem> getGroupItemMap(){
        map.put();
    }*/

    @Autowired private GroupItemDao groupItemDao;

    @Autowired private AccountDao accountDao;
    public List<GroupItem> getGroupItemListBySellerId(String sellerId){
        return groupItemDao.getSellGroupList(sellerId);
    }

    public GroupItem searchItem(String itemId){
        return groupItemDao.getItem(itemId);
    }

    private static GroupItem reqToGroupItem(GroupItemRequest req, int userId){
        //itemId = AutoGenerate
        GroupItem tmp = new GroupItem();
        tmp.setSellerId(userId);
        tmp.setStatus(req.getStatus());
        tmp.setSalesNow(req.getSalesNow());
        tmp.setEndDate(req.getEndDate());
        tmp.setStartDate(req.getStartDate());
        tmp.setDescription(req.getDescription());
        tmp.setPrice(req.getPrice());
        tmp.setTitle(req.getTitle());
        tmp.setImageUrl(req.getImageUrl());
        tmp.setWishCount(req.getWishCount());
        return tmp;
    }

    //게시글 추가 후 itemId 반환
    public String registerNewGroupItem(GroupItemRequest req, int userId){
        return groupItemDao.insertGroupItem(reqToGroupItem(req, userId));
    }

    //게시글 수정 후 itemId 반환
    public String updateGroupItem(GroupItemRequest req, int userId){
       return groupItemDao.updateGroupItem(reqToGroupItem(req, userId));
    }

    //게시글 삭제
    public void deleteGroupItem(String itemId){
        groupItemDao.deleteGroupItem(itemId);
    }

    //공동구매 진행 상황 업데이트
    public void updateStatus(GroupItem groupItem){
        groupItem.updateStatus(groupItem);
    }

}
