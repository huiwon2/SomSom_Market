package com.example.somsom_market.dao;

import com.example.somsom_market.controller.PersonalItem.PersonalItemRequest;
import com.example.somsom_market.domain.ItemStatus;
import com.example.somsom_market.domain.item.PersonalItem;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PersonalItemDao {
    @PersistenceContext
    private EntityManager em;

    public List<PersonalItem> findAll() {
        return em.createQuery("select p from PersonalItem p", PersonalItem.class).getResultList();
    }

    @Transactional
    public PersonalItem insertItem(PersonalItem personalItem) throws DataAccessException {
        em.persist(personalItem);
        return personalItem;
    }

    @Transactional
    public PersonalItem updateItem(PersonalItemRequest itemRegistReq) throws DataAccessException {
        PersonalItem personalItem = em.find(PersonalItem.class, itemRegistReq.getItemId());
        personalItem.setTitle(itemRegistReq.getTitle());
        personalItem.setPrice(itemRegistReq.getPrice());
        personalItem.setDescription(itemRegistReq.getDescription());
        if (itemRegistReq.getStatus().equals("거래가능")) {
            personalItem.setStatus(ItemStatus.INSTOCK);
        } else if (itemRegistReq.getStatus().equals("거래중")) {
            personalItem.setStatus(ItemStatus.ING);
        } else {
            personalItem.setStatus(ItemStatus.SOLDOUT);
        }

        return personalItem;
    }

    @Transactional
    public void deleteItem(PersonalItem personalItem) {
        if (em.contains(personalItem)) {
            em.remove(personalItem);
        } else {
            em.remove(em.merge(personalItem));
        }
    }
}
