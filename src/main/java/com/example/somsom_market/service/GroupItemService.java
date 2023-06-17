package com.example.somsom_market.service;

import com.example.somsom_market.controller.GroupItem.GroupItemRequest;
import com.example.somsom_market.dao.AccountDao;
import com.example.somsom_market.dao.GroupItemDao;
import com.example.somsom_market.domain.item.GroupItem;
import com.example.somsom_market.domain.ItemStatus;
import com.example.somsom_market.repository.GroupItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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

    private static GroupItem reqToGroupItem(GroupItemRequest req, String userId){
        //itemId = AutoGenerate
        GroupItem tmp = new GroupItem();
        tmp.setSellerId(userId);
        tmp.setStatus(ItemStatus.INSTOCK);
        tmp.setSalesNow(req.getSalesNow());
        tmp.setEndDate(req.getEndDate());
        tmp.setStartDate(req.getStartDate());
        tmp.setDescription(req.getDescription());
        tmp.setPrice(req.getPrice());
        tmp.setTitle(req.getTitle());
//        tmp.setImageUrl(req.getImageUrl());
        tmp.setWishCount(req.getWishCount());
        return tmp;
    }

    public GroupItem registerNewGroupItem(GroupItemRequest req, String userId){
        return groupItemDao.insertGroupItem(reqToGroupItem(req, userId));
    }

    public GroupItem updateGroupItem(GroupItemRequest req, String userId){
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
    public List<GroupItem> showGroupItemList(String userId){
        return groupItemRepository.findBySellerIdOrderByStartDate(userId);
    }

    //모든 공동구매 리스트 보여주기
    public List<GroupItem> showAllGroupItemList(){
        return groupItemDao.findAllGroupItem();
    }

    //목표 금액이 모이면 판매자가 상태 바꾸고, 마감 기한 전까지 모이지 않으면 주문 취소하기
    public GroupItem changeStatus(GroupItem groupItem){
        long itemId = groupItem.getId();
        int totalOrdersPrice = groupItemDao.getTotalPriceOfGroupItemOrders(groupItem.getId());
        int salesTarget = groupItem.getSalesTarget();
        Date endDate = groupItem.getEndDate();
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        if(totalOrdersPrice >= salesTarget && endDate.compareTo(today) >= 0){
            groupItemDao.updateStatusToSoldOut(itemId);
        }
        if(totalOrdersPrice < salesTarget && endDate.compareTo(today) < 0){
            groupItemDao.cancelGroupItemOrders(itemId);
        }
        return groupItem;
    }

    public boolean isExistSellingItem(String id) {
        List<GroupItem> groupItems = groupItemRepository.findBySellerIdAndStatus(id, ItemStatus.INSTOCK);
        if (groupItems.size() > 0) {
            return true;
        }
        return false;
    }
}
