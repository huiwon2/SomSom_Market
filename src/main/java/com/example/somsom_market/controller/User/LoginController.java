package com.example.somsom_market.controller.User;


import com.example.somsom_market.domain.Account;
import com.example.somsom_market.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("userSession")
public class LoginController {
    private static final String USER_LOGIN_FORM = "user/loginForm";

    @Autowired
    private AccountService accountService;
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/user/login")
    public ModelAndView login(HttpServletRequest request,
                              @RequestParam("id") String id, @RequestParam("password") String password,
                              @RequestParam(value="forwardAction", required=false) String forwardAction,
                              Model model) throws Exception {

        Account account = accountService.getAccount(id, password);
        if (account == null) {
            return new ModelAndView("redirect:loginForm", "message",
                    "아이디 또는 패스워드가 잘못되었습니다.");
        } else {
            UserSession userSession = new UserSession(account);
            model.addAttribute("userSession", userSession);

            if (forwardAction != null)
                return new ModelAndView("redirect:" + forwardAction);
            else
                return new ModelAndView("redirect:" + "/");
        }
    }
}
