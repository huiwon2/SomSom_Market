package com.example.somsom_market.controller.GroupItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class GroupItemOrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/group/item/{itemId}") //공동구매 아이템 주문
    public String order(HttpSession session, @ModelAttribute("orderSubmitRequest") OrderSubmitRequest req, @PathVariable int itemId, Model model){ // 결제 시스템 쪽에서 validate 할거임 // 이렇게 세션에서 받아오던가
        int orderId = orderService.submitOrder(req);
        List<Order> orderList = orderService.getOrders(); // 수정 필요
        model.addAttribute("orderList");
        return "/user/myPage/orderList";
    }

    @RequestMapping("/user/myPage/order/cancel")
    public String cancelOrder(@RequestParam("itemId") int itemId, Model model){
        //수정 필요
        model.addAttribute("orderList", list);
        return "/user/myPage/orderList";
    }
}
