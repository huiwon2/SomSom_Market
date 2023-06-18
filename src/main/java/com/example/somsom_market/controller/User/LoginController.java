package com.example.somsom_market.controller.User;


import com.example.somsom_market.domain.Account;
import com.example.somsom_market.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("userSession")
public class LoginController {
    private static final String USER_LOGIN_FORM = "user/loginForm";
    private static final String USER_FIND_ID_FORM = "user/findIdForm";

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
            ModelAndView mav = new ModelAndView(USER_LOGIN_FORM);
            mav.addObject("message", "아이디 또는 패스워드가 잘못되었습니다.");
            return mav;
        } else {
            UserSession userSession = new UserSession(account);
            model.addAttribute("userSession", userSession);

            if (forwardAction != null)
                return new ModelAndView("redirect:" + forwardAction);
            else
                return new ModelAndView("redirect:" + "/");
        }
    }

    @GetMapping("/user/findId")
    public String findIdShow() {
        return USER_FIND_ID_FORM;
    }

    @PostMapping("/user/findId")
    @ResponseBody
    public List<String> findId(@RequestParam("email") String email,
                                    @RequestParam("phone") String phone) {
        List<Account> accountList = accountService.getIdByEmailAndPhone(email, phone);
        List<String> idList = new ArrayList<>();
        for (Account ac : accountList) {
            idList.add(ac.getId());
        }
        return idList;
    }
}
