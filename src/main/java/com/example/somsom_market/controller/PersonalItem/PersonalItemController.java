package com.example.somsom_market.controller.PersonalItem;

import com.example.somsom_market.controller.ItemRegistRequest;
import com.example.somsom_market.domain.PersonalItem;
import com.example.somsom_market.service.PersonalItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("personalItem")
public class PersonalItemController {
    private static final String PERSONAL_REGISTRATION_FORM = "personal/itemRegisterForm";
    private static final String PERSONAL_UPDATE_FORM = "personal/itemUpdateForm";

    @Autowired
    private PersonalItemService personalItemService;
    public void setPersonalItemService(PersonalItemService personalItemService) {
        this.personalItemService = personalItemService;
    }

    @ModelAttribute("personalItem")
    public ItemRegistRequest formBackingObject (HttpServletRequest request) {
        ItemRegistRequest itemRegistRequest = (ItemRegistRequest) request.getSession().getAttribute("personalItem");
        if (itemRegistRequest == null) {
            itemRegistRequest = new ItemRegistRequest();
        }
        return itemRegistRequest;
    }

    @PostMapping("/personal/register")
    public ModelAndView register(HttpServletRequest request,
                                 @ModelAttribute("personalItem") ItemRegistRequest itemRegistReq,
                                 BindingResult result, SessionStatus status) {
        // 입력 값 검증 추후 수정

        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.setViewName(PERSONAL_REGISTRATION_FORM);
            return mav;
        }

        PersonalItem personalItem = personalItemService.registerNewItem(itemRegistReq);
        String viewName = "personal/" + personalItem.getItemId(); // personal/{itemId}
        mav.setViewName(viewName);
        mav.addObject("personalItem", personalItem);

        status.setComplete();

        return mav;
    }

    @PostMapping("/user/myPage/sell/personal/update")
    public ModelAndView update(HttpServletRequest request,
                               @ModelAttribute("personalItem") ItemRegistRequest itemRegistReq,
                               BindingResult result, SessionStatus status) {

        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.setViewName(PERSONAL_UPDATE_FORM);
            return mav;
        }

        PersonalItem personalItem = personalItemService.updateItem(itemRegistReq);

        String viewName = "personal/" + personalItem.getItemId(); // personal/{itemId}
        mav.setViewName(viewName);
        mav.addObject("personalItem", personalItem);

        status.setComplete();

        return mav;
    }

    @RequestMapping("/user/myPage/sell/personal/delete")
    public String delete(HttpServletRequest request,
                         @RequestParam("itemId") int itemId) {

        personalItemService.deleteItem(itemId);

        return "personal";
    }
}
