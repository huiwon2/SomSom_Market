package com.example.somsom_market.controller.SomsomItem;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Cart;
import com.example.somsom_market.domain.CartItem;
import com.example.somsom_market.domain.item.Item;
import com.example.somsom_market.domain.item.SomsomItem;
import com.example.somsom_market.service.AccountService;
import com.example.somsom_market.service.CartService;
import com.example.somsom_market.service.SomsomItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes("userSession")
public class CartController {
    @Autowired
    AccountService accountService;
    @Autowired
    SomsomItemService somsomItemService;
    @Autowired
    CartService cartService;
//    장바구니에 물건 담기
    @PostMapping("/somsom/cart/{id}/{item_id}")
    public String addCartItem(@PathVariable("id") String id, @PathVariable("item_id") Long itemId, int amount){
        Account account = accountService.getAccount(id);

        SomsomItem item = somsomItemService.findOne(itemId);

        cartService.addCart(account, item, amount);
        return "redirect:/somsom/cart/cart";

    }

//      장바구니 페이지 접속
    @GetMapping("/somsom/cart")
    public String userCartPage(HttpServletRequest request, Model model){
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

            Cart userCart = cartService.findUserCart(account.getId());
            List<CartItem> cartItemList = cartService.allUserCartView(userCart);
            int totalPrice = 0;
            for(CartItem cartItem : cartItemList){
                totalPrice += cartItem.getCount() + cartItem.getItem().getPrice();
            }
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalCount", userCart.getCount());
            model.addAttribute("cartItems", cartItemList);
//            model.addAttribute("user", accountService.getAccount(id));

            return "items/somsom/cart/cart";
    }

//    장바구니 물건 삭제
    @GetMapping("/somsom/cart/{id}/{cartItemId}/delete")
    public String deleteCartItem(@PathVariable("id") String id, @PathVariable("cartItemId") Long itemId, Model model, UserSession userSession){
        if(userSession.getAccount().getId() == id){
            CartItem cartItem = cartService.findCartItemById(itemId);
            Cart userCart = cartService.findUserCart(id);

            cartService.cartItemDelete(itemId);

            List<CartItem> cartItemList = cartService.allUserCartView(userCart);

            int totalPrice = 0;
            for(CartItem cartitem : cartItemList){
                totalPrice += cartitem.getCount() * cartitem.getItem().getPrice();
            }
            userCart.setCount(userCart.getCount() - cartItem.getCount());

            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalCount", userCart.getCount());
            model.addAttribute("cartItems", cartItemList);
            model.addAttribute("user", accountService.getAccount(id));

            return "/somsom/cart";
        }
        // 로그인 id와 장바구니 삭제하려는 유저의 id가 같지 않는 경우
        else {
            return "redirect:/";
        }
    }
//    @PostMapping

}
