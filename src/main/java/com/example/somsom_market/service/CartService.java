package com.example.somsom_market.service;

import com.example.somsom_market.dao.CartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    CartDao cartDao;

}
