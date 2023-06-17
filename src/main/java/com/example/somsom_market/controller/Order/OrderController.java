package com.example.somsom_market.controller.Order;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Cart;
import com.example.somsom_market.domain.Order;
import com.example.somsom_market.domain.OrderItem;
import com.example.somsom_market.domain.item.SomsomItem;
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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes("userSession")
public class OrderController {

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
        Account account = userSession.getAccount();
        SomsomItem item = somsomItemService.findOne(itemId);
        int count = 1;

        model.addAttribute("account", account);
        model.addAttribute("item", item);
        model.addAttribute("count", count);

        return "order/orderForm";
    }

//    //주문서 폼 생성
//    @GetMapping("/order")
//    public String createForm(HttpServletRequest request,
//                             Model model,
//                             @RequestParam("itemId") Long itemId) {
//
//        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
//        Account account = userSession.getAccount();
//        SomsomItem item = somsomItemService.findOne(itemId);
//
//        model.addAttribute("account", account);
//        model.addAttribute("item", item);
//
//        return "order/orderForm";
//    }

    @PostMapping(value = "/order/{itemId}/{count}")
    public String orderInsert(HttpServletRequest request,
                              @PathVariable Long itemId,
                              @PathVariable int count) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        String accountId = userSession.getAccount().getId();

        orderService.insertOrder(accountId, itemId, count);

        return "order/orderList";
    }

//
//    @PostMapping(value = "/order/{itemId}/{count}")
//    public String orderInsert(HttpServletRequest request,
//                              @PathVariable Long itemId,
//                              @PathVariable int count) {
//        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
//        String accountId = userSession.getAccount().getId();
//
//        orderService.insertOrder(accountId, itemId, count);
//
//        return "redirect:order/confirm";
//    }

//    @PostMapping(value = "/order")
//    public String orderInsert(HttpServletRequest request,
//                              Model model,
//                              @RequestParam Long itemId,
//                              @RequestParam int count,
//                              @ModelAttribute("orderQuantity") String orderQuantity) {
//        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
//        Account account = accountService.getAccount(userSession.getAccount().getId());
//        SomsomItem item = somsomItemService.getSomsomItem(itemId);
//
//        model.addAttribute("account", account);
//        model.addAttribute("item", item);
//        model.addAttribute("orderQuantity", orderQuantity);
//
//        return "order/confirm";
//    }



//    //주문 객체를 생성하고 "order"라는 이름으로 모델에 추가
//    @ModelAttribute("orderForm")
//    public OrderForm createOrderForm() {
//        return new OrderForm();
//    }
//
//    //결제수단 객체를 생성하고 "paymentTypes"라는 이름으로 모델에 추가
//    @ModelAttribute("paymentTypes")
//    public List<String> referenceData() {
//        ArrayList<String> paymentTypes = new ArrayList<String>();
//        paymentTypes.add("무통장 입금");
//        return paymentTypes;
//    }
//
//    //주문서 폼 생성
//    @GetMapping("/order")
//    public String createForm(HttpServletRequest request,
//                             Model model,
//                             @RequestParam("itemId") Long itemId) {
//
//        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
//        Account account = accountService.getAccount(userSession.getAccount().getId());
//        SomsomItem item = somsomItemService.getSomsomItem(itemId);
//
//        model.addAttribute("account", account);
//        model.addAttribute("item", item);
//
//        return "redirect:order/orderForm";
//    }
//
//    //주문서 제출
//    @PostMapping("/order")
//    public String registerOrder(HttpServletRequest request,
//                                @RequestParam("itemId") Long itemId,
//                                @RequestParam("count") int count,
//                                SessionStatus status,
//                                BindingResult result) {
//        if (result.hasErrors()) return "order/orderForm";
//
//        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
//        String accountId = userSession.getAccount().getId();
//        SomsomItem item = somsomItemService.getSomsomItem(itemId);
//
//        orderService.order(accountId, orderForm.getOrder());
//        status.setComplete();  // remove sessionCart and orderForm from session
//
//        return "order/confirm";
//    }
//
//    //주문 정보 확인 및 결제 안내
//    @GetMapping("/order/confirm")
//    public String confirmOrder(
//            HttpServletRequest request,
//            @ModelAttribute("orderForm") OrderForm orderForm,
//            Model model) {
//        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
//        String accountId = userSession.getAccount().getId();
//
//        model.addAttribute("order", orderForm.getOrder());
//        return "home";
//    }

}
