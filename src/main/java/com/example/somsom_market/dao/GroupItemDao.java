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
public class GroupItemDao extends ItemDao{
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
        long itemId = groupItem.getItemId();
        return itemId;
    }
    @Transactional
    public long updateGroupItem(GroupItem groupItem){
        em.merge(groupItem);
        long itemId = groupItem.getItemId();
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
}
