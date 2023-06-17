package com.example.somsom_market.controller.Order;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Cart;
import com.example.somsom_market.service.AccountService;
import com.example.somsom_market.service.OrderService;
import com.example.somsom_market.service.OrderValidator;
import com.example.somsom_market.service.SomsomItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"sessionCart", "orderForm"})
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final SomsomItemService somsomItemService;
    private final AccountService accountService;

    @Autowired
    private OrderValidator orderValidator;

    //주문 객체를 생성하고 "order"라는 이름으로 모델에 추가
    @ModelAttribute("orderForm")
    public OrderForm createOrderForm() {
        return new OrderForm();
    }

    //결제수단 객체를 생성하고 "paymentTypes"라는 이름으로 모델에 추가
    @ModelAttribute("paymentTypes")
    public List<String> referenceData() {
        ArrayList<String> paymentTypes = new ArrayList<String>();
        paymentTypes.add("무통장 입금");
        return paymentTypes;
    }

    //주문서 생성, 카트 바탕으로 주문 페이지 가져오기
    @GetMapping("/order")
    public String initNewOrder(HttpServletRequest request,
                               @ModelAttribute("sessionCart") Cart cart,
                               @ModelAttribute("orderForm") OrderForm orderForm,
                               Model model
    ) throws ModelAndViewDefiningException {
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        if (cart != null) {
            Account account = accountService.getAccount(userSession.getAccount().getId());
            orderForm.getOrder().initOrder(account, cart);
            model.addAttribute("orderReq", createOrderForm());
            return "order/orderForm";
        }
        else {
            ModelAndView modelAndView = new ModelAndView("/error");
            modelAndView.addObject("message", "An order could not be created because a cart could not be found.");
            throw new ModelAndViewDefiningException(modelAndView);
        }
    }

    //주문서 제출
    @PostMapping("/order/submitted")
    public String registerOrder(HttpServletRequest request,
                                @ModelAttribute("orderForm") OrderForm orderForm,
                                BindingResult result) {
        if (result.hasErrors()) return "order/orderForm";

        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        String accountId = userSession.getAccount().getId();

        orderService.order(accountId, orderForm.getOrder());
        return "order/confirm";
    }

    //주문 정보 확인 및 결제 안내
    @GetMapping("/order/confirm")
    public String confirmOrder(
            HttpServletRequest request,
            @ModelAttribute("orderForm") OrderForm orderForm,
            SessionStatus status,
            Model model) {

        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        String accountId = userSession.getAccount().getId();

        orderService.order(accountId, orderForm.getOrder());
        model.addAttribute("order", orderForm.getOrder());
        status.setComplete();  // remove sessionCart and orderForm from session
        return "home";
    }

//
//    //주문 목록 조회
//    @GetMapping("/orders")
//    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
//        List<Order> orders = orderService.findOrders(orderSearch);
//        model.addAttribute("orders", orders);
//
//        return "order/orderList";
//    }
//
//    @PostMapping("/{orderId}/cancel") // TODO: 2023/05/27  마이페이지에서 접근
//    public String cancelOrder(@PathVariable("orderId") Long orderId) {
//        orderService.cancelOrder(orderId);
//        return "redirect:/"; // TODO: 2023/05/27 마이페이지로 리다이렉트
//    }

//    // GET 요청이 들어오면 "orderReq" 모델 속성이 모델에 추가되고 ORDER 뷰를 반환
//    @GetMapping("/order/form")
//    public String createForm(Model model) {
//        model.addAttribute("orderReq", new OrderForm());
//        return "/order/form";
//    }
//
//    //POST 요청이 들어오면 OrderRequest 객체가 세션에 저장되고 BindingResult를 통해 유효성 검사 결과를 확인
//    @PostMapping("/order/form")
//    public String create(HttpServletRequest request,
//                         @Valid @ModelAttribute("orderForm") OrderForm orderForm,
//                         BindingResult result) throws ModelAndViewDefiningException {
//        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession")
//        if (result.hasErrors()) {
//            return "/order/form";
//        } else {
//            Account account = accountService.getAccount(userSession.getAccount().getId());
//            Order order = Order.createOrder(account, orderForm)
//            Date orderDate = new Date();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//
//            // TODO: 2023/05/27 데이터 매핑
//            order.setOrderDate(simpleDateFormat.format(orderDate));
//            order.setTotalPrice(orderRequest.getTotalPrice());
//
//            // Order 객체를 세션에 저장
//            model.addAttribute("order", order);
//            status.setComplete();
//
//            return "redirect:/order/payment";
//        }
//    }
//

}
