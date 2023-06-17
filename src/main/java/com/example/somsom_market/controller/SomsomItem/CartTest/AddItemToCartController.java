package com.example.somsom_market.controller.SomsomItem.CartTest;

import com.example.somsom_market.domain.CartTest.CartTest;
import com.example.somsom_market.domain.item.SomsomItem;
import com.example.somsom_market.service.AccountService;
import com.example.somsom_market.service.CartService;
import com.example.somsom_market.service.SomsomItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"sessionCart", "orderForm"})
public class AddItemToCartController {
    @Autowired
    AccountService accountService;
    @Autowired
    SomsomItemService somsomItemService;
    @Autowired
    CartService cartService;

    //카트 객체 생성
    @ModelAttribute("sessionCart")
    public CartTest createCart() {
        return new CartTest();
    }

    //장바구니에 물건 담기
    @RequestMapping("/cart/addItemToCart")
    public ModelAndView handleRequest(
            @RequestParam("workingItemId") Long workingItemId,
            @ModelAttribute("sessionCart") CartTest cart
    ) throws Exception {
        if (cart.containsItemId(workingItemId)) {
            cart.incrementQuantityByItemId(workingItemId);
        }
        else {
            // isInStock is a "real-time" property that must be updated
            // every time an item is added to the cart, even if other
            // item details are cached.

            SomsomItem item = somsomItemService.findOne(workingItemId);
            boolean isInStock = item.isItemInStock(item.getId());
            cart.addItem(item, isInStock);
        }
        return new ModelAndView("items/somsom/cart/cart", "cart", cart);
    }
}
