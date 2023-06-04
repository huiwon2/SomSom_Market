package com.example.somsom_market.controller.Order;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Cart;
import com.example.somsom_market.domain.Order;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final SomsomItemService somsomItemService;
    private final AccountService accountService;

    @Autowired
    private OrderValidator orderValidator;

    // 주문 객체를 생성하고 "order"라는 이름으로 모델에 추가
    @ModelAttribute("orderForm")
    public OrderForm createOrderForm() {
        return new OrderForm();
    }

    // 결제수단 객체를 생성하고 "paymentTypes"라는 이름으로 모델에 추가
    @ModelAttribute("paymentTypes")
    public List<String> referenceData() {
        ArrayList<String> paymentTypes = new ArrayList<String>();
        paymentTypes.add("무통장 입금");
        return paymentTypes;
    }

    @RequestMapping("/shop/newOrder.do")
    public String initNewOrder(HttpServletRequest request,
                               @ModelAttribute("sessionCart") Cart cart,
                               @ModelAttribute("orderForm") OrderForm orderForm
    ) throws ModelAndViewDefiningException {
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        if (cart != null) {
            // Re-read account from DB at team's request.
            Account account = accountService.getAccount(userSession.getAccount().getId());
            orderForm.getOrder().initOrder(account, cart);
            return "NewOrderForm";
        }
        else {
            ModelAndView modelAndView = new ModelAndView("Error");
            modelAndView.addObject("message", "An order could not be created because a cart could not be found.");
            throw new ModelAndViewDefiningException(modelAndView);
        }
    }

    // GET 요청이 들어오면 "orderReq" 모델 속성이 모델에 추가되고 ORDER 뷰를 반환
    @GetMapping("/order/form")
    public String createForm(Model model) {
        model.addAttribute("orderReq", new OrderForm());
        return "/order/form";
    }

    // POST 요청이 들어오면 OrderRequest 객체가 세션에 저장되고 BindingResult를 통해 유효성 검사 결과를 확인
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

    @RequestMapping("/shop/newOrderSubmitted.do")
    public String bindAndValidateOrder(HttpServletRequest request,
                                       @ModelAttribute("orderForm") OrderForm orderForm,
                                       BindingResult result) {
        if (orderForm.didShippingAddressProvided() == false) {
            // from NewOrderForm
            orderValidator.validateCreditCard(orderForm.getOrder(), result);
            orderValidator.validateBillingAddress(orderForm.getOrder(), result);
            if (result.hasErrors()) return "NewOrderForm";

            if (orderForm.isShippingAddressRequired() == true) {
                orderForm.setShippingAddressProvided(true);
                return "ShippingForm";
            }
            else {
                return "ConfirmOrder";
            }
        }
        else {		// from ShippingForm
            orderValidator.validateShippingAddress(orderForm.getOrder(), result);
            if (result.hasErrors()) return "ShippingForm";
            return "ConfirmOrder";
        }
    }

//    @RequestMapping("/shop/confirmOrder.do")
//    protected ModelAndView confirmOrder(
//            @ModelAttribute("orderForm") OrderForm orderForm,
//            SessionStatus status) {
//        orderService.order(orderForm.getOrder());
//        ModelAndView mav = new ModelAndView("ViewOrder");
//        mav.addObject("order", orderForm.getOrder());
//        mav.addObject("message", "Thank you, your order has been submitted.");
//        status.setComplete();  // remove sessionCart and orderForm from session
//        return mav;
//    }


    @GetMapping("/order/payment")
    public String showOrderPaymentPage(@ModelAttribute("order") Order order) {
        return "/order/payment";
    }

    @GetMapping("/order/complete")
    public String showCompletePage(@ModelAttribute("order") Order order) {
        return "/order/complete";
    }

    @GetMapping("/main")
    public String backToMain() {
        return "/main";
    }

    @PostMapping("/{orderId}/cancel") // TODO: 2023/05/27  마이페이지에서 접근
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/"; // TODO: 2023/05/27 마이페이지로 리다이렉트
    }
}
