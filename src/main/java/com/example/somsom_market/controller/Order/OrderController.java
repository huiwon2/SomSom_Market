package com.example.somsom_market.controller.Order;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Order;
import com.example.somsom_market.domain.item.SomsomItem;
import com.example.somsom_market.service.AccountService;
import com.example.somsom_market.service.OrderService;
import com.example.somsom_market.service.SomsomItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes({"sessionCart", "orderForm"})
@Slf4j
public class OrderController { //상세페이지에서 바로 결제

    private final OrderService orderService;
    private final SomsomItemService somsomItemService;
    private final AccountService accountService;

    //결제수단 객체를 생성하고 "paymentTypes"라는 이름으로 모델에 추가
    @ModelAttribute("paymentTypes")
    public List<String> referenceData() {
        ArrayList<String> paymentTypes = new ArrayList<String>();
        paymentTypes.add("무통장 입금");
        return paymentTypes;
    }

    //주문서 폼 생성
    @GetMapping("/order/{itemId}")
    public String createForm(HttpServletRequest request,
                             Model model,
                             @PathVariable("itemId") Long itemId) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        if (userSession == null) {
            return "redirect:/user/loginForm";
        }

        Account account = userSession.getAccount();
        SomsomItem item = somsomItemService.findOne(itemId);
        int count = 1;

        model.addAttribute("account", account);
        model.addAttribute("item", item);
        model.addAttribute("count", count);

        return "order/orderRegisterForm";
    }

    //주문서 제출
    @PostMapping(value = "/order/{itemId}/{count}")
    public String orderInsert(HttpServletRequest request,
                              @PathVariable Long itemId,
                              @PathVariable int count,
                              Model model) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        String accountId = userSession.getAccount().getId();

        orderService.insertOrder(accountId, itemId, count);

        Account account = userSession.getAccount();
        List<Order> orders = orderService.findOrders(account.getId());
        model.addAttribute("userOrderList", orders);

        return "user/myPage/orderList";
    }

}
