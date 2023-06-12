package com.example.somsom_market.controller.SomsomItem;

import com.example.somsom_market.service.AccountService;
import com.example.somsom_market.service.SomsomItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("userSession")
public class CartController {
    AccountService accountService;
    SomsomItemService somsomItemService;
//    장바구니에 물건 담기
//    @PostMapping("/user/cart/{id}/{item_id}")
//    public String addCartItem(@PathVariable("id") String id, @PathVariable("item_id") long itemId, int amount){
//        Account account = accountService.getAccount(id);
//        Item item = somsomItemService.
//    }

}
