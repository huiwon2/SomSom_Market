package com.example.somsom_market.controller.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    @RequestMapping("/user/logout")
    public String handleRequest(HttpSession session) throws Exception {
        session.removeAttribute("userSession");
        session.invalidate();
        return "redirect:/main";
    }
}
