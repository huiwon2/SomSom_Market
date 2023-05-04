package com.example.somsom_market.controller.User;

import com.idle.somsommarket.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    private static final String USER_LOGIN_FORM = "user/loginForm";

    @Autowired
    private AccountService accountService;
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/user/login")
    public String handleRequest(HttpServletRequest request,
                                @RequestParam("id") String id, @RequestParam("password") String password) {

        Account account = accountService.getAccount(id, password);
        if (account == null) {
            return USER_LOGIN_FORM;
        } else {
            // 세션에 추가

            return "redirect:/main.jsp"; // 메인화면으로 redirection
        }
    }
}
