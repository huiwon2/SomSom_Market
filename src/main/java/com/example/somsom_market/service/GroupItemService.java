package com.example.somsom_market.service;

import com.example.somsom_market.controller.GroupItem.GroupItemRequest;
import com.example.somsom_market.dao.AccountDao;
import com.example.somsom_market.dao.GroupItemDao;
import com.example.somsom_market.domain.GroupItem;
import com.example.somsom_market.repository.GroupItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GroupItemService {

    @Autowired private GroupItemDao groupItemDao;
    @Autowired
    private GroupItemRepository groupItemRepository;
    @Autowired
    private AccountDao accountDao;

    public GroupItem searchItem(long itemId){
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
    public long registerNewGroupItem(GroupItemRequest req, int userId){
        return groupItemDao.insertGroupItem(reqToGroupItem(req, userId));
    }

    //게시글 수정 후 itemId 반환
    public long updateGroupItem(GroupItemRequest req, int userId){
       return groupItemDao.updateGroupItem(reqToGroupItem(req, userId));
    }

    //게시글 삭제
    public void deleteGroupItem(long itemId){
        groupItemDao.deleteGroupItem(itemId);
    }

    //공동구매 진행 상황 업데이트
    public void updateStatus(GroupItem groupItem){
        groupItemDao.updateStatus(groupItem);
    }

    //사용자가 판매하는 공동구매 리스트 보여주기
//    public List<GroupItem> showGroupItemList(int userId){
//        return groupItemRepository.findGroupItemsBySellerIdOrderByStartDate(userId);
//    }

    //모든 공동구매 리스트 보여주기
    public List<GroupItem> showAllGroupItemList(){
        return groupItemDao.findAllGroupItem();
    }
}
