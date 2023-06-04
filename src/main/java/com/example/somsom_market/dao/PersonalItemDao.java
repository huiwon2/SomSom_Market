package com.example.somsom_market.dao;

import com.example.somsom_market.domain.PersonalItem;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PersonalItemDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public PersonalItem insertItem(PersonalItem personalItem) throws DataAccessException {
        em.persist(personalItem);
        return personalItem;
    }
}
