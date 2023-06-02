package com.example.somsom_market.controller.User;

import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.GroupItem;
import com.example.somsom_market.domain.Order;
import com.example.somsom_market.domain.PersonalItem;
import com.example.somsom_market.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user/myPage")
@SessionAttributes("userSession")
/* 마이페이지 */
public class ViewUserItemController {
    private static final String VIEW = "user/myPage";
    private static final String SELL_PERSONAL = "user/myPage/personalList";
    private static final String SELL_GROUP = "user/myPage/groupList";
    private static final String ORDER = "user/myPage/orderList";

    @Autowired
    private AccountService accountService;
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    // 구매 내역, 판매 내역, 공동구매 내역, 위시리스트 개수 보여주는 페이지
    @GetMapping
    public String view(HttpServletRequest request, Model model) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        int[] myPageList = accountService.getMyPageList(account.getId());

        model.addAttribute("myPageList", myPageList);

        return VIEW;
    }

    // 사용자 판매 내역 (개인)
    @GetMapping("/sell/personal")
    public ModelAndView viewSellList(HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        List<PersonalItem> userPersonalSellList = accountService.getSellItemList(account.getId());

        ModelAndView mav = new ModelAndView(SELL_PERSONAL);
        mav.addObject("userPersonalSellList", userPersonalSellList);

        return mav;
    }

    // 사용자 공동구매 판매 내역
    @GetMapping("/sell/group")
    public ModelAndView viewSellGroupList(HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        List<GroupItem> userGroupSellList = accountService.getSellGroupList(account.getId());

        ModelAndView mav = new ModelAndView(SELL_GROUP);
        mav.addObject("userGroupSellList", userGroupSellList);

        return mav;
    }

    // 사용자 구매 내역
    @GetMapping("/order")
    public ModelAndView viewOrderList(HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        List<Order> userOrderList = accountService.getOrderList(account.getId());

        ModelAndView mav = new ModelAndView(ORDER);
        mav.addObject("userOrderList", userOrderList);

        return mav;
    }

}
