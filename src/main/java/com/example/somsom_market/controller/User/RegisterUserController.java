package com.example.somsom_market.controller.User;


import com.example.somsom_market.domain.Account;
import com.example.somsom_market.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/register")
@SessionAttributes({"memReq", "userSession"})
public class RegisterUserController {

    private static final String USER_REGISTRATION_FORM = "user/registerForm";
    @Autowired
    private AccountService accountService;
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @ModelAttribute("memReq")
    public UserRegistRequest formBacking(HttpServletRequest request) {
        UserRegistRequest userReq = new UserRegistRequest();
        return userReq;
    }

    @GetMapping
    public String showForm() {
        return USER_REGISTRATION_FORM;
    }

    @PostMapping
    public String submit(@ModelAttribute("memReq") UserRegistRequest memReq, // 추후에 입력 값 검증 추가
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return USER_REGISTRATION_FORM;
        }
        
        // id 중복 확인 (추후 수정)

        Account account = accountService.insertAccount(memReq); // 계정 생성
        UserSession userSession = new UserSession(account); // account를 세션에 저장
        model.addAttribute("userSession", userSession);
        // -> 따로 로그인하지 않아도 회원가입 완료하면 자동 로그인

        return "redirect:/main.jsp"; // 메인화면으로 redirection
    }
}
