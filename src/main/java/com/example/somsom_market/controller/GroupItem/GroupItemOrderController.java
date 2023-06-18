package com.example.somsom_market.controller.GroupItem;

import com.example.somsom_market.controller.Order.OrderRequest;
import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Order;
import com.example.somsom_market.domain.OrderItem;
import com.example.somsom_market.domain.item.GroupItem;
import com.example.somsom_market.domain.item.SomsomItem;
import com.example.somsom_market.service.GroupItemOrderService;
import com.example.somsom_market.service.GroupItemService;
import com.example.somsom_market.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("userSession")
public class GroupItemOrderController {
    @Autowired
    private GroupItemService groupService;
    @Autowired
    private GroupItemOrderService orderService;
    private final String MY_GROUP_LIST = "user/myPage/groupList";

   /* @RequestMapping("")
    public String changeStatusBySeller(@RequestParam("itemId") Long itemId, Model model){
        GroupItem groupItem = groupService.searchItem(itemId);
        groupService.changeStatus(groupItem);
        model.addAttribute("groupItem", groupItem);

        return MY_GROUP_LIST;

    }*/
    //결제수단 객체를 생성하고 "paymentTypes"라는 이름으로 모델에 추가
    @ModelAttribute("paymentTypes")
    public List<String> referenceData() {
        ArrayList<String> paymentTypes = new ArrayList<String>();
        paymentTypes.add("무통장 입금");
        return paymentTypes;
    }

    //주문서 폼 생성
    @GetMapping("/order/group/{itemId}")
    public String createForm(HttpServletRequest request,
                             Model model,
                             @PathVariable("itemId") Long itemId) {


        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        if (userSession == null) {
            return "redirect:/user/loginForm";
        }

        Account account = userSession.getAccount();
        GroupItem item = groupService.searchItem(itemId);
        int count = 1;

        model.addAttribute("account", account);
        model.addAttribute("item", item);
        model.addAttribute("count", count);

        return "order/groupOrderForm";
    }

    //주문서 제출
    @PostMapping(value = "/order/group/{itemId}/{count}")
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

//    @PostMapping(value = "/order/group/{itemId}/{count}")
//    public String orderInsert(HttpServletRequest request,
//                              @PathVariable Long itemId,
//                              @PathVariable int count) {
//        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
//        String accountId = userSession.getAccount().getId();
//
//        OrderItem orderItem = orderService.insertOrder(accountId, itemId, count);
//        int price = orderItem.getOrderPrice();
//        orderService.addToSalesNow(price, itemId);
//        orderService.minusToStockQuantity(count, itemId);
//        return "order/orderList";
//    }

    @PostMapping("/orders/group/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        OrderItem orderItem = orderService.cancelOrder(orderId);
        int price = orderItem.getOrderPrice();
        int count = orderItem.getQuantity();
        long itemId = orderItem.getItem().getId();
        orderService.addToSalesNow(-1*price, itemId);
        orderService.minusToStockQuantity(-1*count, itemId);
        return "redirect:/orders";
    }

}
