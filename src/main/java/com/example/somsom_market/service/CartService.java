package com.example.somsom_market.service;

import com.example.somsom_market.dao.CartDao;
import com.example.somsom_market.domain.Cart;
import com.example.somsom_market.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    CartDao cartDao;
    CartRepository cartRepository;


}
