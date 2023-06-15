package com.example.somsom_market.controller.PersonalItem;

import com.example.somsom_market.controller.User.UserSession;
import com.example.somsom_market.domain.Account;
import com.example.somsom_market.domain.Notes;
import com.example.somsom_market.domain.item.PersonalItem;
import com.example.somsom_market.service.AccountService;
import com.example.somsom_market.service.NotesService;
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

    @Autowired
    private NotesService notesService;
    public void setNotesService(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("/personal/chat/send")
    public String showChatSendForm(HttpServletRequest request,
                                   @RequestParam("itemId2") Long itemId, Model model) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");

        if (userSession == null) {
            return "redirect:/user/loginForm";
        }

        PersonalItem personalItem = personalItemService.searchItem(itemId);
        Account account = accountService.getAccount(personalItem.getSellerId());
        personalItem.setNickName(account.getNickName());

        model.addAttribute("item", personalItem);
        model.addAttribute("error", "false");
        return PERSONAL_CHAT_FORM;
    }

    @PostMapping("/personal/chat/send")
    public String chatSend(HttpServletRequest request,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("itemId") Long itemId,
                           Model model) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        PersonalItem personalItem = personalItemService.searchItem(itemId);
        boolean success = notesService.insertNotes(
                account.getId(), itemId, personalItem.getSellerId(), title, content);

        if (!success) { // 쪽지 보내기에 실패하면 다시 보내기 창으로
            Account seller = accountService.getAccount(personalItem.getSellerId());
            personalItem.setNickName(seller.getNickName());

            model.addAttribute("item", personalItem);
            model.addAttribute("error", "true");
            return PERSONAL_CHAT_FORM;
        }

        return "redirect:/personal/chat/list";
    }


    @RequestMapping("/personal/chat/list")
    public String showList(HttpServletRequest request, Model model) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");

        if (userSession == null) {
            return "redirect:/user/loginForm";
        }

        Account account = userSession.getAccount();

        List<Notes> receivedNotes = notesService.receivedNotes(account.getId());
        List<Notes> sendedNotes = notesService.sendedNotes(account.getId());

        model.addAttribute("receivedNotes", receivedNotes);
        model.addAttribute("sendedNotes", sendedNotes);

        return PERSONAL_CHAT_LIST;
    }

    @GetMapping("/personal/chat/{chatId}")
    @ResponseBody
    public Notes showChatDetail(HttpServletRequest request,
                                 @RequestParam("chatId") Long chatId) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        return null;
    }




}
