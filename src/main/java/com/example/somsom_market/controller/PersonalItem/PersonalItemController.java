package com.example.somsom_market.controller.PersonalItem;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.PersonalItem;
import com.example.somsom_market.service.AccountService;
import com.example.somsom_market.service.PersonalItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("userSession")
/* 개인거래 게시글 등록, 수정, 삭제를 관리하는 컨트롤러 */
public class PersonalItemController {
    private static final String PERSONAL_REGISTRATION_FORM = "personal/itemRegisterForm";
    private static final String PERSONAL_DETAIL_VIEW = "personal/detail";
    private static final String LOGIN_FORM = "user/loginForm";

    @Autowired
    private PersonalItemService personalItemService;
    public void setPersonalItemService(PersonalItemService personalItemService) {
        this.personalItemService = personalItemService;
    }

    @Autowired
    private AccountService accountService;
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/personal/register")
    public ModelAndView showRegisterForm(HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        ModelAndView mav = new ModelAndView();

        if (userSession != null) { // 로그인 되어 있으면
            mav.setViewName(PERSONAL_REGISTRATION_FORM);
            mav.addObject("statusString", new String[] {"거래가능", "거래중", "거래완료"});
            mav.addObject("personalItem", new PersonalItemRequest());
            return mav;
        } else {
            mav.setViewName(LOGIN_FORM);
            return mav;
        }
    }

    @PostMapping("/personal/register")
    public ModelAndView register(HttpServletRequest request,
                                 @ModelAttribute("personalItem") PersonalItemRequest itemRegistReq,
                                 BindingResult result) {
        // 입력 값 검증 추후 수정

        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.setViewName(PERSONAL_REGISTRATION_FORM);
            return mav;
        }

        PersonalItem personalItem = personalItemService.registerNewItem(itemRegistReq, account.getId());
        mav.setViewName(PERSONAL_DETAIL_VIEW);
        mav.addObject("personalItem", personalItem);
        mav.addObject("seller", account);

        return mav;
    }

    @GetMapping("/user/myPage/sell/personal/update")
    public ModelAndView showUpdateForm(HttpServletRequest request, @RequestParam("itemId") int itemId) {
        ModelAndView mav = new ModelAndView();

        PersonalItem personalItem = personalItemService.searchItem(itemId);
        PersonalItemRequest personalItemRequest = new PersonalItemRequest();

        personalItemRequest.setTitle(personalItem.getTitle());
        personalItemRequest.setPrice(personalItem.getPrice());
        personalItemRequest.setDescription(personalItem.getDescription());
        personalItemRequest.setStatus(personalItem.getStatus());


        mav.setViewName(PERSONAL_REGISTRATION_FORM);
        mav.addObject("statusString", new String[] {"거래가능", "거래중", "거래완료"});
        mav.addObject("personalItem", personalItemRequest);

        return mav;
    }

    @PostMapping("/user/myPage/sell/personal/update")
    public ModelAndView update(HttpServletRequest request,
                               @ModelAttribute("personalItem") PersonalItemRequest itemRegistReq,
                               BindingResult result) {

        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.setViewName(PERSONAL_REGISTRATION_FORM);
            return mav;
        }

        PersonalItem personalItem = personalItemService.updateItem(itemRegistReq);
        mav.setViewName(PERSONAL_DETAIL_VIEW);
        mav.addObject("personalItem", personalItem);
        mav.addObject("seller", account);

        return mav;
    }

    @RequestMapping("/user/myPage/sell/personal/delete")
    public String delete(HttpServletRequest request,
                         @RequestParam("itemId") int itemId) {

        personalItemService.deleteItem(itemId);

        return "personal";
    }
}
