package com.example.somsom_market.controller.SomsomItem;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Cart;
import com.example.somsom_market.domain.CartItem;
import com.example.somsom_market.domain.item.Item;
import com.example.somsom_market.service.AccountService;
import com.example.somsom_market.service.CartService;
import com.example.somsom_market.service.SomsomItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes("userSession")
public class CartController {
    AccountService accountService;
    SomsomItemService somsomItemService;
    CartService cartService;
//    장바구니에 물건 담기
//    @PostMapping("/user/cart/{id}/{item_id}")
//    public String addCartItem(@PathVariable("id") String id, @PathVariable("item_id") long itemId, int amount){
//        Account account = accountService.getAccount(id);
//    각각 아이디 타입이 다른데..ㅜㅜ
//        Item item = somsomItemService.itemView(id);
//
//        cartService.addCart(account, item, amount);
//        return "redirect:/somsomItem/view/{itemId}";
//
//    }

//      장바구니 페이지 접속
//    @GetMapping("/user/cart/{id}")
//    public String userCartPage(@PathVariable("id")String id, Model model, UserSession userSession){
//        if(userSession.getAccount().getId() == id){
//            Account account = accountService.getAccount(id);
//
////            Cart userCart = account.getCart();
//            List<CartItem> cartItemList = cartService.
//        }
//    }
}
