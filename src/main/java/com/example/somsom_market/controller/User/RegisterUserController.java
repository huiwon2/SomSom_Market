package com.example.somsom_market.controller.User;

import com.idle.somsommarket.controller.User.UserRegistRequest;
import com.idle.somsommarket.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/register")
@SessionAttributes("memReq")
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

    @RequestMapping(method = RequestMethod.GET)
    public String showForm() {
        return USER_REGISTRATION_FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute("memReq") UserRegistRequest memReq, // 추후에 입력 값 검증 추가
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return USER_REGISTRATION_FORM;
        }

        Account account = accountService.insertAccount(memReq); // 계정 생성
        // account를 세션에 저장 (추후에 클래스 만들어지면 수정)
        // -> 따로 로그인하지 않아도 회원가입 완료하면 자동 로그인

        return "redirect:/main.jsp"; // 메인화면으로 redirection
    }
}
