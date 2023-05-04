package com.example.somsom_market.controller.Order;

import com.example.somsom_market.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("order")
public class OrderController {
//    @Autowired
//    private OrderService orderService;
    private static final String ORDER = "/order";
    private static final String ORDER_PAYMENT = "/order/payment";
    private static final String ORDER_COMPLETE = "/order/complete";
    private static final String MAIN = "/main";

    // 주문 객체를 생성하고 "order"라는 이름으로 모델에 추가
    @ModelAttribute("order")
    public Order createOrder() {
        return new Order();
    }

    // 결제수단 객체를 생성하고 "paymentType"라는 이름으로 모델에 추가
    @ModelAttribute("paymentType")
    public List<String> referenceData() {
        ArrayList<String> paymentType = new ArrayList<String>();
        paymentType.add("무통장 입금");
        return paymentType;
    }

    // GET 요청이 들어오면 "orderReq" 모델 속성이 모델에 추가되고 ORDER 뷰를 반환
    @GetMapping(ORDER)
    public String showOrderForm(Model model) {
        model.addAttribute("orderReq", new OrderRequest());
        return ORDER;
    }

    // POST 요청이 들어오면 OrderRequest 객체가 세션에 저장되고 BindingResult를 통해 유효성 검사 결과를 확인
    @PostMapping(ORDER)
    public String processOrderForm(
            @ModelAttribute("orderReq") @Valid OrderRequest orderRequest,
            BindingResult result, SessionStatus status, Model model) {
        if (result.hasErrors()) {
            // TODO: 유효성 검사 로직 필요
            return ORDER;
        } else {
            Order order = new Order();
            Date orderDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

            order.setUserName(orderRequest.getUserName());
            order.setOrderDate(simpleDateFormat.format(orderDate));
            order.setPhoneNumber(orderRequest.getPhoneNumber());
            order.setShipAddress(orderRequest.getShipAddress());
            order.setShipState("0");
            order.setTotalPrice(orderRequest.getTotalPrice());
            order.setStatus(0);
            order.setItemType(0); // TODO: 타입 구분 로직 필요

            // Order 객체를 세션에 저장
            model.addAttribute("order", order);
            status.setComplete();

            return "redirect:" + ORDER_PAYMENT;
        }
    }

    @GetMapping(ORDER_PAYMENT)
    public String showOrderPaymentPage(@ModelAttribute("order") Order order) {
        return ORDER_PAYMENT;
    }

    @GetMapping(ORDER_COMPLETE)
    public String showCompletePage(@ModelAttribute("order") Order order) {
        return ORDER_COMPLETE;
    }

    @GetMapping(MAIN)
    public String backToMain() {
        return MAIN;
    }

}
