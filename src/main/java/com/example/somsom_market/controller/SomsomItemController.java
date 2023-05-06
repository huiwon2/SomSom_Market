package com.example.somsom_market.controller;


import com.example.somsom_market.service.SomsomItemService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public class SomsomItemController {
//    mvc설계 보고 경로 채우기
    private static final String SOMSOM_REGISTRATION_FORM = "";
    private static final String SOMSOM_UPDATE_FORM = "";
    private static final String ITEM_NOT_FOUND = "";

    @Autowired
    @Setter
    private SomsomItemService somsomItemService;

//    Register
//    form(register method)
    @RequestMapping(method = RequestMethod.GET)
    public String form() {
        return SOMSOM_REGISTRATION_FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("registerReq")ItemRegistRequest itemRegistRequest,
                           BindingResult bindingResult,
                           Model model){

        if (bindingResult.hasErrors()) {
            return SOMSOM_REGISTRATION_FORM;
        }
//        String itemId = SomsomItemService.registerNewItem(itemRegisterRequest);
//        model.addAttribute("itemId", itemId);
        return "";
    }
//    Update(Service 설계 필요, 사용자 예외 설정 필요한지 판단 필요)
//    form(Update method)
//    @RequestMapping(method = RequestMethod.GET)
//    public String form(ItemUpdateRequest itemUpdateRequest, @RequestParam("itemId") ItemId itemId) {
//        ItemInfo itemInfo = somsomItemService.getItemInfo(itemId);
//        if(itemInfo == null){
//            return ITEM_NOT_FOUND;
//        }
//        itemUpdateRequest.setTitle(itemInfo.getTitle());
//        itemUpdateRequest.setPrice(itemInfo.getPrice());
//        itemUpdateRequest.setDescription(itemInfo.getDescription());
//        itemUpdateRequest.setImageUrl(itemInfo.getImageUrl());
//        return SOMSOM_UPDATE_FORM;
//    }
//    @RequestMapping(method = RequestMethod.POST)
//    public String update(@ModelAttribute("updateReq") ItemUpdateRequest itemUpdateRequest, Errors errors) {
//        if (errors.hasErrors()) {
//            return SOMSOM_UPDATE_FORM;
//        }
//        try {
//            somsomItemService.updateItemInfo(itemUpdateRequest);
//            return "member/modified";
//        } catch (NotMatchPasswordException ex) {
//            errors.rejectValue("currentPassword", "invalidPassword");
//            return SOMSOM_UPDATE_FORM;
//        } catch (ItemNotFoundException ex) {
//            return ITEM_NOT_FOUND;
//        }
//    }

}
