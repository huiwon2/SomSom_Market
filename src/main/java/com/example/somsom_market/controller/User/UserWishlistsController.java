package com.example.somsom_market.controller.User;

import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.GroupItem;
import com.example.somsom_market.domain.PersonalItem;
import com.example.somsom_market.domain.SomsomItem;
import com.example.somsom_market.service.AccountService;
import com.example.somsom_market.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user/myPage/wishlist")
@SessionAttributes("userSession")
/* 사용자 위시리스트 */
public class UserWishlistsController {
    private static final String VIEW = "user/myPage/wishlistList";

    @Autowired
    private AccountService accountService;
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    private WishlistService wishlistService;
    public void setWishlistService(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping // 위시리스트 내역 보기
    public ModelAndView view(HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        List<PersonalItem> userWishlistPersonalItem = wishlistService.getPersonalWishlist(account.getId());
        List<GroupItem> userWishlistGroupItem = wishlistService.getGroupWishlist(account.getId());

        ModelAndView mav = new ModelAndView(VIEW);
        mav.addObject("userWishlistPersonalItem", userWishlistPersonalItem);
        mav.addObject("userWishlistGroupItem", userWishlistGroupItem);

        return mav;
    }

    @ResponseBody
    @PostMapping("/add")
    public void add(HttpServletRequest request,
                    @RequestParam("itemId") int itemId) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        wishlistService.addWishlist(account.getId(), itemId);
    }

    @ResponseBody
    @PostMapping("/delete")
    public void cancel(HttpServletRequest request,
                    @RequestParam("itemId") int itemId) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        wishlistService.cancelWishlist(account.getId(), itemId);
    }
}
