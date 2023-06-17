package com.example.somsom_market.dao;

import com.example.somsom_market.domain.item.GroupItem;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class GroupItemDao{
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public GroupItem getItem(Long itemId){
        GroupItem groupItem = em.find(GroupItem.class, itemId);
        return groupItem;
    }
    @Transactional
    public GroupItem insertGroupItem(GroupItem groupItem) throws DataAccessException {
        em.persist(groupItem);
        return groupItem;
    }
    @Transactional
    public GroupItem updateGroupItem(GroupItem groupItem){
        GroupItem tmp = em.find(GroupItem.class, groupItem.getId());
        if(tmp != null){
            em.merge(groupItem);
        }
        return groupItem;
    }
    @Transactional
    public void deleteGroupItem(Long itemId){
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

    // 공구 판매자 ---> 모인 총 금액 확인
    @Transactional
    public int getTotalPriceOfGroupItemOrders(Long itemId){
        Query query = em.createNativeQuery("SELECT SUM(o.ORDER_PRICE) FROM ORDER_ITEM o JOIN ITEM i WHERE o.ITEM_ID = ?");
        query.setParameter(1, itemId);
        return (int) query.getSingleResult();
    }

    // 공구 판매자 ---> 마감 기한까지 모금액이 안모였을 경우, 주문 취소 상태로 일괄 변경
    //... OrderItem이 somsomItem 전용 클래스라 작동 안함... 수정 필요//////
    @Transactional
    public int cancelGroupItemOrders(long itemId){
        //Query query = em.createQuery("DELETE OrderItem o WHERE o.id = :itemId");
        //query.setParameter("itemId", itemId);
        //int deletedCnt = query.executeUpdate();
        //return deletedCnt;
        Query query = em.createQuery("UPDATE OrderItem o SET o.item.status = :status WHERE o.item.id = :itemId");
        query.setParameter("status", "CANCEL");
        query.setParameter("itemId", itemId);
        int updateCnt = query.executeUpdate();
        return updateCnt;
    }



    //공구 마감
    @Transactional
    public int updateStatusToSoldOut(long itemId){
        Query query = em.createQuery("UPDATE GroupItem g SET g.status = :status WHERE g.id = :itemId");
        query.setParameter("status","SOLDOUT");
        query.setParameter("itemId", itemId);
        int updateCnt = query.executeUpdate();
        return updateCnt;
    }
}
