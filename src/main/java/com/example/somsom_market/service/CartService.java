package com.example.somsom_market.service;

import com.example.somsom_market.dao.CartDao;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Cart;
import com.example.somsom_market.domain.CartItem;
import com.example.somsom_market.domain.Item;
import com.example.somsom_market.repository.AccountRepository;
import com.example.somsom_market.repository.CartItemRepository;
import com.example.somsom_market.repository.CartRepository;
import com.example.somsom_market.repository.SomsomItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CartService {
    CartDao cartDao;
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    SomsomItemRepository somsomItemRepository;

//    장바구니 추가
    @Transactional
    public void addCart(Account account, Item newItem, int amount){
        Cart cart = cartRepository.findById(account.getId());
//        장바구니 없을 때
        if(cart == null){
            cart = Cart.createCart(account);
            cartRepository.save(cart);
        }
        Item item = somsomItemRepository.findById(newItem.getId());
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getCart_id(), item.getId());
//        장바구니에 아이템 없을 때
        if(cartItem == null){
            cartItem = CartItem.createCartItem(cart, item, amount);
            cartItemRepository.save(cartItem);
        }
//        아이템 있을 때 수량 증가
        else{
            CartItem updated = cartItem;
            updated.setCart_id(cartItem.getCart_id());
            updated.setItem(cartItem.getItem());
            updated.addCount(amount);
            updated.setCount(updated.getCount());
            cartItemRepository.save(updated);
        }

        cart.setCount(cart.getCount() + amount);


    }



}
