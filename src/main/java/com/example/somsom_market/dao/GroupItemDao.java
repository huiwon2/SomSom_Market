package com.example.somsom_market.dao;

import com.example.somsom_market.domain.GroupItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class GroupItemDao{
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public GroupItem getItem(long itemId){
        GroupItem groupItem = em.find(GroupItem.class, itemId);
        return groupItem;
    }
    @Transactional
    public long insertGroupItem(GroupItem groupItem){
        em.persist(groupItem);
        long itemId = groupItem.getId();
        return itemId;
    }
    @Transactional
    public long updateGroupItem(GroupItem groupItem){
        em.merge(groupItem);
        long itemId = groupItem.getId();
        return itemId;
    }
    @Transactional
    public void deleteGroupItem(long itemId){
        GroupItem groupItem = em.find(GroupItem.class, itemId);
        em.remove(em.merge(groupItem));
    }

    @Transactional
    public void updateStatus(GroupItem groupItem){
        em.merge(groupItem);
    }

    @Transactional
    public List<GroupItem> findAllGroupItem(){
        Query query = em.createQuery("SELECT g FROM GroupItem g ORDER BY g.endDate DESC");
        List<GroupItem> groupItems = query.getResultList();
        return groupItems;
    }

    @Transactional
    public int getTotalPriceOfGroupItemOrders(long itemId){
        Query query = em.createNativeQuery("SELECT SUM(o.ORDER_PRICE) FROM ORDER_ITEM o JOIN ITEM i WHERE o.ITEM_ID = ?");
        query.setParameter(1, itemId);
        return (int) query.getSingleResult();
    }

    @Transactional
    public int cancelGroupItemOrders(long itemId){
        Query query = em.createQuery("DELETE OrderItem o WHERE o.id = :itemId");
        query.setParameter("itemId", itemId);
        int deletedCnt = query.executeUpdate();
        return deletedCnt;
    }

    @Transactional
    public int updateStatusToSoldOut(long itemId){
        Query query = em.createQuery("UPDATE GroupItem g SET g.status = :status WHERE g.id = :itemId");
        query.setParameter("status","SOLDOUT");
        query.setParameter("itemId", itemId);
        int updateCnt = query.executeUpdate();
        return updateCnt;
    }

    //public int changeOrderStatus(long itemId){

    //}
}
