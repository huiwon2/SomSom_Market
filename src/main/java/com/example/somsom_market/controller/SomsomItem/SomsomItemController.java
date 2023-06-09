package com.example.somsom_market.controller.SomsomItem;


import com.example.somsom_market.dao.SomsomItemDao;
import com.example.somsom_market.domain.SomsomItem;
import com.example.somsom_market.service.SomsomItemService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Controller
@SessionAttributes("userSession")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SomsomItemController {
//    mvc설계 보고 경로 채우기
    private static final String SOMSOM_REGISTRATION_FORM = "/somsomItem/somsomItemRegister";
    private static final String SOMSOM_UPDATE_FORM = "/somsomItem/somsomItemUpdate";
    private static final String ITEM_NOT_FOUND = "";

    private SomsomItemService  somsomItemService;
    @Autowired
    private SomsomItemDao somsomItemDao;

    private SomsomItem somsomItem;


//    Register
//    form(register method)
    @GetMapping("somsomItem/somsomItemRegister")
    public String form() {
        return SOMSOM_REGISTRATION_FORM;
    }

    @PostMapping("somsomItem/somsomItemRegister/product")
    public String register(@Valid @ModelAttribute("registerReq") ItemRegistRequest itemRegistRequest,
                           BindingResult bindingResult,
                           Model model){

        if (bindingResult.hasErrors()) {
            return SOMSOM_REGISTRATION_FORM;
        }
        Long itemId = somsomItem.getId();
        model.addAttribute("itemId", itemId);
        somsomItemService.saveItem(somsomItem);
        return "/main";
    }
//    form(Update method)
    @GetMapping("somsomItem/update/{item_id}")
    public String form(ItemUpdateRequest itemUpdateRequest, @RequestParam("itemId")Long itemId, Model model) {
        SomsomItem itemInfo = somsomItemService.getSomsomItem(itemId);
        if(itemInfo == null){
            return ITEM_NOT_FOUND;
        }
        itemUpdateRequest.setTitle(itemInfo.getTitle());
        itemUpdateRequest.setPrice(itemInfo.getPrice());
        itemUpdateRequest.setDescription(itemInfo.getDescription());
//        itemUpdateRequest.setImageUrl(itemInfo.getImageUrl().toString());//?
        model.addAttribute("item", somsomItemService.itemView(itemId));
        return SOMSOM_UPDATE_FORM;
    }
    @PostMapping("somsomItem/update/product/{item_id}")
    public String update(@ModelAttribute("updateReq") ItemUpdateRequest itemUpdateRequest, Errors errors) {
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
//    솜솜아이템 리스트

//    상세 페이지
    @GetMapping("somsomItem/somsomItemview/{item_id}")
    public String itemView(Model model, @PathVariable("itemId")Long itemId){
        model.addAttribute("somsomItmem", somsomItemService.itemView(itemId));

        return "somsomItem/itemView";
    }

}
