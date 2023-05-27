package com.example.somsom_market.dao;

import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.SomsomItem;
import lombok.Data;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Data
public class SomsomItemDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertSomsomItem(SomsomItem somsomItem) throws DataAccessException {
        entityManager.persist(somsomItem);
    }

    @Transactional
    public void updateSomsomItem(SomsomItem somsomItem) throws DataAccessException {
        entityManager.merge(somsomItem);
    }
    @Transactional
    public void deleteSomsomItem(SomsomItem somsomItem) {
        if (entityManager.contains(somsomItem)) {
            entityManager.remove(somsomItem);
        } else {
            entityManager.remove(entityManager.merge(somsomItem));
        }
    }
}
