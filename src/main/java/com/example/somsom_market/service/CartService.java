package com.example.somsom_market.service;

import com.example.somsom_market.dao.CartDao;
import com.example.somsom_market.domain.*;
import com.example.somsom_market.domain.item.SomsomItem;
import com.example.somsom_market.repository.CartItemRepository;
import com.example.somsom_market.repository.CartRepository;
import com.example.somsom_market.repository.SomsomItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartDao cartDao;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    SomsomItemRepository somsomItemRepository;

//    장바구니 추가
    @Transactional
    public void addCart(Account account, SomsomItem newItem, int amount){
        Cart cart = cartRepository.findByAccountId(account.getId());
//        장바구니 없을 때
        if(cart == null){
            cart = Cart.createCart(account);
            cart = cartRepository.save(cart);
        }
//        Optional<SomsomItem> item = somsomItemRepository.findById(newItem.get().getId());
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), newItem.getId());
//        장바구니에 아이템 없을 때
        if(cartItem == null){
            cartItem = CartItem.createCartItem(cart, newItem, amount);
            cartItemRepository.save(cartItem);
        }
//        아이템 있을 때 수량 증가
        else{
            CartItem updated = cartItem;
            updated.setCart(cartItem.getCart());
            updated.setItem(cartItem.getItem());
            updated.addCount(amount);
            updated.setCount(updated.getCount());
            cartItemRepository.save(updated);
        }

        cart.setCount(cart.getCount() + amount);


    }
    public List<CartItem> allUserCartView(Cart cart){
        return cartItemRepository.findAll();
    }


    public CartItem findCartItemById(Long itemId) {
        return cartItemRepository.findByItemId(itemId);
    }

    public Cart findUserCart(String id) {
        Cart cart = cartRepository.findByAccountId(id);
        return cart;
    }

    public void cartItemDelete(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }
}
