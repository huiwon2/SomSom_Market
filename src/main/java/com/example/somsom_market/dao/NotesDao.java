package com.example.somsom_market.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class NotesDao {
    @PersistenceContext
    private EntityManager em;


}
