package com.example.somsom_market.controller.PersonalItem;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.item.PersonalItem;
import com.example.somsom_market.service.PersonalItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SessionAttributes("userSession")
/* 개인거래 게시글 등록, 수정, 삭제를 관리하는 컨트롤러 */
public class PersonalItemController {
    private static final String PERSONAL_REGISTRATION_FORM = "items/personal/itemRegisterForm";
    private static final String PERSONAL_DETAIL_VIEW = "items/personal/detail";
    private static final String LOGIN_FORM = "user/loginForm";

    @Autowired
    private PersonalItemService personalItemService;
    public void setPersonalItemService(PersonalItemService personalItemService) {
        this.personalItemService = personalItemService;
    }

    @GetMapping("/personal/register")
    public ModelAndView showRegisterForm(HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        ModelAndView mav = new ModelAndView();

        if (userSession != null) { // 로그인 되어 있으면
            mav.setViewName(PERSONAL_REGISTRATION_FORM);
            mav.addObject("statusString", new String[] {"거래가능", "거래중", "거래완료"});
            PersonalItemRequest personalItem = new PersonalItemRequest();
            personalItem.setItemId((long) -1);
            mav.addObject("personalItem", personalItem);
            return mav;
        } else {
            mav.setViewName(LOGIN_FORM);
            return mav;
        }
    }

    @PostMapping("/personal/register")
    public String register(HttpServletRequest request,
                                 @ModelAttribute("personalItem") PersonalItemRequest itemRegistReq,
                                 BindingResult result) {
        // 입력 값 검증 추후 수정

        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        if (result.hasErrors()) {
            return PERSONAL_REGISTRATION_FORM;
        }

        PersonalItem personalItem = personalItemService.registerNewItem(itemRegistReq, account.getId());

        return "redirect:/personal/detail/" + personalItem.getId(); // 추후 수정
        // "redirect:/item/" + personalItem.getId();
    }

    @GetMapping("/user/myPage/sell/personal/update")
    public ModelAndView showUpdateForm(HttpServletRequest request,
                                       @RequestParam("itemId") Long itemId) {
        ModelAndView mav = new ModelAndView();

        PersonalItem personalItem = personalItemService.searchItem(itemId);

        PersonalItemRequest personalItemRequest = new PersonalItemRequest();

        personalItemRequest.setItemId(personalItem.getId());
        personalItemRequest.setSellerId(personalItem.getSellerId());
        personalItemRequest.setTitle(personalItem.getTitle());
        personalItemRequest.setPrice(personalItem.getPrice());
        personalItemRequest.setDescription(personalItem.getDescription());
        if (personalItem.getStatus().toString().equals("INSTOCK")) {
            personalItemRequest.setStatus("거래가능");
        } else if (personalItem.getStatus().toString().equals("ING")) {
            personalItemRequest.setStatus("거래중");
        } else {
            personalItemRequest.setStatus("거래완료");
        }

        mav.setViewName(PERSONAL_REGISTRATION_FORM);
        mav.addObject("statusString", new String[] {"거래가능", "거래중", "거래완료"});
        mav.addObject("personalItem", personalItemRequest);

        return mav;
    }

    @PostMapping("/user/myPage/sell/personal/update")
    public String update(HttpServletRequest request,
                               @ModelAttribute("personalItem") PersonalItemRequest itemRegistReq,
                               BindingResult result) {
        // 입력 값 검증 추후 수정

        if (result.hasErrors()) {
            return PERSONAL_REGISTRATION_FORM;
        }

        PersonalItem personalItem = personalItemService.updateItem(itemRegistReq);

        return "redirect:/personal/detail/" + personalItem.getId(); // 추후 수정
        // "redirect:/item/" + personalItem.getId();
    }

    @RequestMapping("/user/myPage/sell/personal/delete")
    public String delete(HttpServletRequest request,
                         @RequestParam("itemId") Long itemId) {

        personalItemService.deleteItem(itemId);

        return "redirect:/personal/list"; // 추후 수정
    }




    // 여기서부턴 추후 삭제 (확인해보기 위해 추가함)

    // 개인 거래 게시글 리스트
    @GetMapping("/personal/list")
    public String showList(HttpServletRequest request, Model model) {

        List<PersonalItem> personalItemList = personalItemService.personalItemList();

        model.addAttribute("personalItemList", personalItemList);

        return "items/personal/list";
    }

    // 개인 거래 게시글 상세 뷰
    @RequestMapping("/personal/detail/{itemId}")
    public String showPersonalDetail(HttpServletRequest request,
                                     @PathVariable("itemId") long itemId, Model model) {
        PersonalItem personalItem = personalItemService.searchItem(itemId);

        model.addAttribute("personalItem", personalItem);

        return PERSONAL_DETAIL_VIEW;
    }
}
