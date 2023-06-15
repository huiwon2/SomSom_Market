package com.example.somsom_market.controller.PersonalItem;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Notes;
import com.example.somsom_market.domain.item.PersonalItem;
import com.example.somsom_market.service.AccountService;
import com.example.somsom_market.service.PersonalItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SessionAttributes("userSession")
public class NotesController {
    private static final String PERSONAL_CHAT_FORM = "items/personal/chat/registerForm";
    private static final String PERSONAL_CHAT_LIST = "items/personal/chat/list";

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

    @GetMapping("/personal/chat/send")
    public String showChatSendForm(HttpServletRequest request,
                                   @RequestParam("itemId2") long itemId, Model model) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");

        if (userSession == null) {
            return "redirect:/user/loginForm";
        }

        PersonalItem personalItem = personalItemService.searchItem(itemId);
        Account account = accountService.getAccount(personalItem.getSellerId());
        personalItem.setNickName(account.getNickName());

        model.addAttribute("item", personalItem);
        return PERSONAL_CHAT_FORM;
    }

    @PostMapping("/personal/chat/send")
    public String charSend() {


        return "";
    }


    @RequestMapping("/personal/chat/list")
    public String showList(HttpServletRequest request, Model model) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");

        if (userSession == null) {
            return "redirect:/user/loginForm";
        }

        Account account = userSession.getAccount();

        return PERSONAL_CHAT_LIST;
    }




}
