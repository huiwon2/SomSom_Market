package com.example.somsom_market.controller.User;

import com.example.somsom_market.domain.Account;
import com.example.somsom_market.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/myPage")
@SessionAttributes("userSession")
public class UpdateUserController {
    private static final String USER_MYPAGE_FORM = "user/myPageUpdateForm";
    @Autowired
    private AccountService accountService;
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @ModelAttribute("accountReq")
    public UserRegistRequest formBacking(HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
            Account account = userSession.getAccount();

            UserRegistRequest accountReq = new UserRegistRequest(
                    account.getUserName(), account.getNickName(), account.getId(),
                    account.getEmail(), account.getAddress(), account.getBankName(),
                    account.getBankAccount(), account.getPhone()
            );

            return accountReq;
        }
        return new UserRegistRequest();
    }


    @GetMapping
    public String showForm() {
        return USER_MYPAGE_FORM;
    }

    @PostMapping("/update")
    public String update(HttpServletRequest request,
                         @ModelAttribute("accountReq") UserRegistRequest accountReq,
                         BindingResult result) {
        // 닉네임, 전화번호, 이메일, 주소, 은행명, 계좌번호 변경 가능

        // 유효성 검사 -> 추후 수정

        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");

        Account account = userSession.getAccount();
        account.setNickName(accountReq.getNickName());
        account.setPhone(accountReq.getPhoneNumber());
        account.setEmail(accountReq.getEmail());
        account.setAddress(accountReq.getAddress());
        account.setBankName(accountReq.getBankName());
        account.setBankAccount(accountReq.getBankAccount());

        Account newAccount = accountService.updateAccount(account);
        userSession.setAccount(newAccount);

        return "redirect:/" + "user/myPage";
    }

//    @PostMapping("/update/password")
//    public String updatePassword() {
//
//    }

//    @PostMapping("/delete")
//    public String delete() {
//
//    }
}
