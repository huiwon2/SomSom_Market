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
import java.util.ArrayList;
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

        List<Notes> receivedAllNotes = notesService.receivedNotes(account.getId());
        List<Notes> sendedAllNotes = notesService.sendedNotes(account.getId());

        // 사용자가 지운 쪽지는 거르는 작업
        List<Notes> receivedNotes = new ArrayList<>();
        List<Notes> sendedNotes = new ArrayList<>();

        for (Notes note : receivedAllNotes) {
            if (note.getToDel().equals("N")) {
                // 받은 쪽지 = 내가 seller, 그니까 to_del 컬럼 확인 (지우지 않았으면!)
                receivedNotes.add(note);
            }
        }

        for (Notes note : sendedAllNotes) {
            if (note.getFromDel().equals("N")) {
                // 보낸 쪽지 = 내가 account, 그니까 from_del 컬럼 확인 (지우지 않았으면!)
                sendedNotes.add(note);
            }
        }

        model.addAttribute("receivedNotes", receivedNotes);
        model.addAttribute("sendedNotes", sendedNotes);

        return PERSONAL_CHAT_LIST;
    }

    @GetMapping("/personal/chat/detail")
    @ResponseBody
    public Notes showChatDetail(HttpServletRequest request,
                                 @RequestParam("notesId") Long notesId) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account users = userSession.getAccount();

        Notes notes = notesService.searchNotes(notesId);
        PersonalItem personalItem = personalItemService.searchItem(notes.getFromItemId());
        notes.setItemTitle(personalItem.getTitle());

        if (notes.getFromAccountId().equals(users.getId())) { // 보낸 사람 == 현재 사용자
            // 보낸 쪽지함이므로 수신자 (seller) 표시
            Account seller = accountService.getAccount(notes.getToSellerId());
            String name = "수신자 : " + seller.getNickName();
            notes.setToSellerNickName(name);
        } else { // 받는 사람 == 사용자
            Account account = accountService.getAccount(notes.getFromAccountId());
            String name = "발신자 : " + account.getNickName();
            notes.setFromAccountNickName(name);

            // 열람한 적 없는 쪽지라면 열람 표시
            if (notes.getReadedAt() == null) {
                Notes newNotes = notesService.updateReaded(notes);
                notes.setReadedAt(newNotes.getReadedAt());
                notes.setReadDate(newNotes.getReadDate());
            }
        }
        return notes;
    }

    @PostMapping("/personal/chat/send/{resItemId}/{resNotesId}")
    public String responseSend(HttpServletRequest request,
                               @PathVariable("resItemId") Long itemId,
                               @PathVariable("resNotesId") Long notesId,
                               @RequestParam("resTitle") String title,
                               @RequestParam("resContent") String content) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        Notes notes = notesService.searchNotes(notesId);
        notesService.insertNotes(account.getId(), itemId,
                notes.getFromAccountId(), title, content);

        return "redirect:/personal/chat/list";
    }

    @PostMapping("/personal/chat/delete/{notesId}")
    public String deleteChat(HttpServletRequest request,
                             @PathVariable("notesId") Long notesId) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Account account = userSession.getAccount();

        Notes notes = notesService.searchNotes(notesId);
        notesService.deleteNotes(account, notes);

        return "redirect:/personal/chat/list";
    }



}
