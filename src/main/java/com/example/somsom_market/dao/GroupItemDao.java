package com.example.somsom_market.dao;

import com.example.somsom_market.domain.GroupItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
@Repository
public class GroupItemDao extends ItemDao{
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public GroupItem getItem(String itemId){
        GroupItem groupItem = em.find(GroupItem.class, itemId);
        return groupItem;
    }
    @Transactional
    public String insertGroupItem(GroupItem groupItem){
        em.persist(groupItem);
        String itemId = groupItem.getItemId();
        return itemId;
    }
    @Transactional
    public String updateGroupItem(GroupItem groupItem){
        em.merge(groupItem);
        String itemId = groupItem.getItemId();
        return itemId;
    }
    @Transactional
    public void deleteGroupItem(String itemId){
        GroupItem groupItem = em.find(GroupItem.class, itemId);
        if(em.contains(groupItem)){
            em.remove(groupItem);
        }else {
            em.remove(em.merge(groupItem));
        }
    }

}
