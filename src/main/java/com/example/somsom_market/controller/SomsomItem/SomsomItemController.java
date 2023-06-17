package com.example.somsom_market.controller.SomsomItem;


import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.dao.SomsomItemDao;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Wishlist;
import com.example.somsom_market.domain.item.SomsomItem;
import com.example.somsom_market.service.AccountService;
import com.example.somsom_market.service.SomsomItemService;
import com.example.somsom_market.service.WishlistService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes("userSession")
@RequiredArgsConstructor
public class SomsomItemController {
//    mvc설계 보고 경로 채우기
    private static String SOMSOM_REGISTRATION_FORM = "/somsom/item/somsomItemRegister";
    private static String SOMSOM_UPDATE_FORM = "/somsom/item/somsomItemRegister";
    private static String ITEM_NOT_FOUND = "/somsom/item/notFound";
    private static String ITEM_FORM = "/somsom/item/somsomItemList";

    private static String SOMSOM_ITEM_DETAIL = "/somsom/item/somsomDetail";
    @Autowired
    @Setter
    private SomsomItemService  somsomItemService;
    @Autowired
    @Setter
    private WishlistService wishlistService;
    @Autowired
    private SomsomItemDao somsomItemDao;
    @Autowired
    @Setter
    private AccountService accountService;
    private SomsomItem somsomItem;


//    Register
//    form(register method)
    @GetMapping("/somsom/item/somsomItemRegister")
    public ModelAndView registerForm(HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        ModelAndView modelAndView = new ModelAndView();
        Account account = userSession.getAccount();
        if(isTrueAdmin(account)){
            modelAndView.setViewName(SOMSOM_REGISTRATION_FORM);
            somsomItem.setId((long)-1);
            modelAndView.addObject("somsomItem", somsomItem);
            return modelAndView;
        }
        modelAndView.setViewName(ITEM_FORM);
        return modelAndView;
    }

    @PostMapping("/somsom/item/somsomItemRegister")
    public String register(@Valid @ModelAttribute("registerReq") SomsomItemRegistRequest itemRegistRequest,
                           BindingResult bindingResult,
                           Model model, HttpServletRequest request) throws IOException {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        if(!isTrueAdmin(account)){
            return ITEM_FORM;
        }
        if (bindingResult.hasErrors()) {
            return "redirect:/somsom/item/somsomItemRegister";
        }
        Long itemId = somsomItem.getId();
        model.addAttribute("itemId", itemId);
        somsomItemService.registerSomsomItem(itemRegistRequest, itemId);
        return "redirect:/main";
    }

//    form(Update method)
    @GetMapping("/somsom/item/update/{item_id}")
    public String form(SomsomItemUpdateRequest itemUpdateRequest, @RequestParam("itemId")Long itemId, Model model) {
        SomsomItem itemInfo = somsomItemService.getSomsomItem(itemId);
        if(itemInfo == null){
            return ITEM_NOT_FOUND;
        }
        itemUpdateRequest.setTitle(itemInfo.getTitle());
        itemUpdateRequest.setPrice(itemInfo.getPrice());
        itemUpdateRequest.setDescription(itemInfo.getDescription());
        model.addAttribute("item", somsomItemService.itemView(itemId));
        return SOMSOM_UPDATE_FORM;
    }

    @PostMapping("/somsom/item/update/{item_id}")
    public String update(@ModelAttribute("updateReq") SomsomItemUpdateRequest itemUpdateRequest, Errors errors, HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();
        if(!isTrueAdmin(account)){
            return ITEM_FORM;
        }
        if (errors.hasErrors()) {
            return SOMSOM_UPDATE_FORM;
        }
        try {
            somsomItemService.updateItem(somsomItem, somsomItem.getId());
            return "redirect:/main";
        } catch (ItemNotFoundException ex) {
            return ITEM_NOT_FOUND;
        }
    }

    //솜솜아이템 리스트
    @GetMapping("/somsom/item/somsomItemList")
    public String showList(HttpServletRequest request, Model model) {

        List<SomsomItem> somsomItems = somsomItemService.somsomItemList();
        model.addAttribute("somsomItems", somsomItems);

        return "items/somsom/item/somsomItemList";
    }

//    상세 페이지
    @GetMapping("/somsom/item/somsomDetail/{item_id}")
    public String itemView(HttpServletRequest request, @PathVariable("itemId")Long itemId, Model model){
        String userId;
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        int isExistWish = 0;
        if (userSession != null) {
            Account account = userSession.getAccount();
            Wishlist wishlist = wishlistService.getSomsomWishlistByAccountAndItem(account.getId(), itemId);
            if (wishlist != null) { // 위시리스트에 추가되어 있으면
                isExistWish = 1;
            }
            userId = account.getId();
        } else {
            userId = "false";
        }
        model.addAttribute("somsomItem", somsomItem);
        model.addAttribute("isExistWish", isExistWish);
        model.addAttribute("userId", userId);
        return "items/somsom/item/somsomDetail";
    }
//     관리자 아이디 검증하기
     private boolean isTrueAdmin(Account account){
        if(account.getId().equals("admin")){
            return true;
        }
        return false;
     }
}
