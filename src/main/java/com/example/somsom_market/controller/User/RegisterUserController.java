package com.example.somsom_market.controller.User;


import com.example.somsom_market.domain.Account;
import com.example.somsom_market.service.AccountFormValidator;
import com.example.somsom_market.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user/register")
@SessionAttributes({"memReq", "userSession"})
/* 회원가입 */
public class RegisterUserController {

    private static final String USER_REGISTRATION_FORM = "user/registerForm";
    @Autowired
    private AccountService accountService;
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    private AccountFormValidator accountFormValidator;
    public void setAccountFormValidator(AccountFormValidator accountFormValidator) {
        this.accountFormValidator = accountFormValidator;
    }

    @ModelAttribute("memReq")
    public UserRegistRequest formBacking(HttpServletRequest request) {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");

        String uri = request.getRequestURI();
        if (isLoggedIn(userSession) && !(uri.contains("/checkId"))) {
            Account account = userSession.getAccount();

            UserRegistRequest memReq = new UserRegistRequest(
                    account.getName(), account.getNickName(), account.getId(),
                    account.getEmail(), account.getPhone()
            );
            if (account.getBankAccount() != null) {
                memReq.setBankAccount(account.getBankAccount());
            }
            if (account.getBankName() != null) {
                memReq.setBankName(account.getBankName());
            }
            if (account.getAddress() != null) {
                memReq.setAddress(account.getAddress());
            }
            if (account.getZipcode() != null) {
                memReq.setZipcode(account.getZipcode());
            }
            return memReq;
        } else {
            return new UserRegistRequest();
        }
    }

    private static boolean isLoggedIn(UserSession userSession) {
        return (userSession != null);
    }

    @GetMapping
    public String showForm() {
        return USER_REGISTRATION_FORM;
    }

    @PostMapping
    public String submit(HttpSession session,
                         @Valid @ModelAttribute("memReq") UserRegistRequest memReq,
                         BindingResult bindingResult, Model model) {
        accountFormValidator.validate(memReq, bindingResult);

        if (bindingResult.hasErrors()) {
            return USER_REGISTRATION_FORM;
        }

        Account account = accountService.insertAccount(memReq); // 계정 생성
        UserSession userSession = new UserSession(account); // account를 세션에 저장
        session.setAttribute("userSession", userSession);
        model.addAttribute("userSession", userSession);
        // -> 따로 로그인하지 않아도 회원가입 완료하면 자동 로그인

        return "redirect:/"; // 메인화면으로 redirection
    }

    @PostMapping("/checkId")
    @ResponseBody
    public boolean checkId(@RequestParam String id) {
        return accountService.isIdExist(id);
    }
}
