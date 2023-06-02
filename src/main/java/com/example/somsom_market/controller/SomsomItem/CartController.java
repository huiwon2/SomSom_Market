package com.example.somsom_market.controller.SomsomItem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {
//    장바구니에 물건 담기
    @PostMapping("/user/cart/{id}/{item_id}")
    public String addCartItem(@PathVariable("id") long id, @PathVariable("item_id") long itemId, int amount){

    }

}
